package erland.webapp.tvguide.act.subscription;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import com.echomine.common.ParseException;
import com.echomine.common.SendMessageFailedException;
import com.echomine.jabber.*;
import com.echomine.net.ConnectionFailedException;
import erland.util.StringUtil;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.act.BaseTaskPlugin;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.tvguide.entity.account.UserAccount;
import erland.webapp.tvguide.fb.program.ProgramPB;
import erland.webapp.tvguide.logic.program.ProgramHelper;
import erland.webapp.usermgmt.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SubscriptionPlugin extends BaseTaskPlugin {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(SubscriptionPlugin.class);
    private static SubscriptionPlugin me = null;
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("d MMM");

    private WebAppEnvironmentInterface environment;
    private WebAppEnvironmentInterface getEnvironment() {
        if(environment == null) {
            environment = WebAppEnvironmentPlugin.getEnvironment();
        }
        return environment;
    }

    public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
        super.init(actionServlet, moduleConfig);
        me = this;
        addTask("mailnotifier",new MailNotifierTask());
        addTask("jabbernotifier",new JabberNotifierTask());
    }

    public static SubscriptionPlugin getInstance() {
        return me;
    }

    private class MailNotifierTask implements Runnable {
        public void run() {
            while (true) {
                Date notificationTime = StringUtil.asDate(getEnvironment().getConfigurableResources().getParameter("notificationtime"), null, TIME_FORMAT);
                Date now = new Date();
                if (notificationTime == null) {
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    notificationTime = c.getTime();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(notificationTime);
                while (c.getTime().before(now)) {
                    c.add(Calendar.DATE, 1);
                }
                notificationTime = c.getTime();

                while (true) {
                    now = new Date();
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                    }
                    if (notificationTime.before(now)) {
                        break;
                    }
                }
                String smtpServer = StringUtil.asNull(getEnvironment().getConfigurableResources().getParameter("smtp.server"));
                String smtpFrom = StringUtil.asNull(getEnvironment().getConfigurableResources().getParameter("smtp.from"));
                if (smtpServer != null && smtpFrom != null) {
                    QueryFilter filter = new QueryFilter("all");
                    EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-useraccount").search(filter);
                    Session session = null;
                    if (entities.length > 0) {
                        Properties props = new Properties();
                        props.put("mail.smtp.host", smtpServer);
                        session = Session.getDefaultInstance(props);
                    }
                    for (int i = 0; i < entities.length; i++) {
                        UserAccount account = (UserAccount) entities[i];
                        if (account.getMailNotification() != null && account.getMailNotification().booleanValue()) {
                            User template = (User) getEnvironment().getEntityFactory().create("usermgmt-user");
                            template.setUsername(account.getUsername());
                            User user = (User) getEnvironment().getEntityStorageFactory().getStorage("usermgmt-user").load(template);
                            if (user != null && StringUtil.asNull(user.getMail()) != null) {
                                try {
                                    InternetAddress from = new InternetAddress(smtpFrom);
                                    InternetAddress to = new InternetAddress(user.getMail());

                                    ProgramPB[] programsPB = ProgramHelper.getSubscribedPrograms(getEnvironment(), account.getUsername(), new Date(), null,null,null,null,null,null);
                                    ProgramPB[] tipsProgramsPB = new ProgramPB[0];
                                    if(account.getIncludeTips()!=null && account.getIncludeTips().booleanValue()) {
                                        tipsProgramsPB = ProgramHelper.getProgramsWithMinReview(getEnvironment(),account.getUsername(),new Date(), 2,account.getMinTipsReview(),true, null,null,null,null,null,null);
                                    }
                                    if (programsPB.length > 0 || tipsProgramsPB.length>0) {
                                        Message msg = new MimeMessage(session);
                                        msg.setFrom(from);
                                        msg.setRecipient(Message.RecipientType.TO, to);
                                        String subject = StringUtil.asNull(getEnvironment().getConfigurableResources().getParameter("smtp.subject"));
                                        Map parameters = new HashMap();
                                        parameters.put("date", DATE_FORMAT.format(new Date()));
                                        msg.setSubject(ServletParameterHelper.replaceDynamicParameters(subject, parameters));
                                        msg.setSentDate(new Date());
                                        msg.setText(getMessageString(programsPB,tipsProgramsPB));

                                        LOG.info("Sending daily mail to: " + ((InternetAddress) msg.getRecipients(Message.RecipientType.TO)[0]).getAddress());
                                        Transport.send(msg);
                                    }
                                } catch (MessagingException e) {
                                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private class JabberNotifierTask implements Runnable {
        public void run() {
            while (true) {
                Date now = new Date();
                try {
                    String jabberServer = StringUtil.asNull(getEnvironment().getConfigurableResources().getParameter("jabber.server"));
                    String jabberUser = StringUtil.asNull(getEnvironment().getConfigurableResources().getParameter("jabber.user"));
                    String jabberPassword = StringUtil.asNull(getEnvironment().getConfigurableResources().getParameter("jabber.password"));
                    if (jabberServer != null && jabberUser != null && jabberPassword != null) {
                        JabberContext context = new JabberContext(jabberUser, jabberPassword, jabberServer);
                        context.setSSL(false);
                        Jabber jabber = new Jabber();
                        JabberSession session = jabber.createSession(context);
                        session.connect(jabberServer, JabberContext.DEFAULT_PORT);
                        session.getUserService().login();
                        QueryFilter filter = new QueryFilter("all");
                        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-useraccount").search(filter);
                        for (int i = 0; i < entities.length; i++) {
                            UserAccount account = (UserAccount) entities[i];
                            if (StringUtil.asNull(account.getJabberId()) != null && account.getJabberNotification() != null && account.getJabberNotification().booleanValue()) {
                                ProgramPB[] programsPB = ProgramHelper.getSubscribedPrograms(getEnvironment(), account.getUsername(), new Date(), null,null,null,null,null,null);
                                Date soon = new Date();
                                Calendar c = Calendar.getInstance();
                                c.add(Calendar.MINUTE, 10);
                                soon = c.getTime();
                                for (int j = 0; j < programsPB.length; j++) {
                                    ProgramPB program = programsPB[j];
                                    if (program.getStart().before(soon) && program.getStart().after(now)) {
                                        try {
                                            session.getChatService().sendPrivateMessage(new JID(account.getJabberId()), getMessageString(program), false);
                                        } catch (SendMessageFailedException e) {
                                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                        } catch (ParseException e) {
                                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                        }
                                    }
                                }
                            }
                        }
                        Thread.sleep(5000);
                        session.disconnect();
                    }
                } catch (ConnectionFailedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (SendMessageFailedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (JabberMessageException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (UnknownHostException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                try {
                    Thread.sleep(60000*10);
                } catch (InterruptedException e) {
                }
            }
        }
    }
    private String getMessageString(ProgramPB[] programs, ProgramPB[] tipsPrograms) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < programs.length; i++) {
            ProgramPB program = programs[i];
            sb.append("\n"+program.getStartDateDisplay()+"\n"+program.getStartTimeDisplay()+" - "+program.getStopTimeDisplay()+" "+program.getName()+" ("+program.getChannelName()+")");
            sb.append("\n"+StringUtil.wordWrap(program.getDescription(),80));
            sb.append("\n");
        }
        if(tipsPrograms.length>0) {
            sb.append("\n");
            sb.append("*********************** Tips ***********************\n");
            sb.append("\n");
        }
        for (int i = 0; i < tipsPrograms.length; i++) {
            ProgramPB program = tipsPrograms[i];
            sb.append("\n"+program.getStartDateDisplay()+"\n"+program.getStartTimeDisplay()+" - "+program.getStopTimeDisplay()+" "+program.getName()+" ("+program.getChannelName()+")");
            sb.append("\n"+StringUtil.wordWrap(program.getDescription(),80));
            sb.append("\nReview: "+program.getReview());
            sb.append("\n");
        }
        return sb.toString();
    }
    private String getMessageString(ProgramPB program) {
        StringBuffer sb = new StringBuffer();
        sb.append(" \n"+program.getStartTimeDisplay()+" - "+program.getStopTimeDisplay()+" "+program.getName()+" ("+program.getChannelName()+")");
        sb.append("\n"+StringUtil.wordWrap(program.getDescription(),40));
        sb.append("\n");
        return sb.toString();
    }
}
