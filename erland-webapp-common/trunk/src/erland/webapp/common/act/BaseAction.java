package erland.webapp.common.act;

/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
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


import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.ServletParameterHelper;

/**
 * Base class for all other actions, contains common functionallity
 * useful in all actions
 */
public class BaseAction extends Action {

    /**
     * Web application environment object
     */
    private WebAppEnvironmentInterface environment = null;

    /**
     * Default forward when the action fails
     */
    protected static final String FORWARD_FAILURE = "failure";

    /**
     * The default forward when the action succeeds
     */
    protected final static String FORWARD_SUCCESS = "success";

    /**
     * Token to print before short description
     * of an exception.
     */
    private static final String ERROR = " ERROR: ";

    /**
     * Token to print between short and long description
     * of an exception.
     */
    private static final String DETAILS = " DETAILS: ";

    /**
     * Token to print between catched exception and the cause to the
     * catched exception
     */
    private static final String CAUSED_BY = " CAUSED BY: ";

    /**
     * Line feed character.
     */
    private static final char LINE_FEED = '\r';     // (aka u000A);

    /**
     * Set exception to request under public key [Action.EXCEPTION].
     *
     * @param request The HTTP request we are processing
     * @param e The new Exception
     */
    protected void setException(
            HttpServletRequest request,
            Exception e) {
        request.setAttribute(Globals.EXCEPTION_KEY, e);
    }


    /**
     * Return exception stored in request under public key [Gobals.EXCEPTION].
     *
     * @param request The HTTP request we are processing
     */
    protected Exception getException(HttpServletRequest request) {
        return (Exception) request.getAttribute(Globals.EXCEPTION_KEY);
    }


    /**
     * Retrieves a base messages and replaceable
     * parameters from a List, and adds them to an ActionErrors
     * collection.
     *
     * @param request The HTTP request we are processing
     * @param alerts Our ActionMessages collection
     * @param list Our list of replaceable parameters
     */
    protected void mergeAlerts(HttpServletRequest request, ActionMessages alerts, List list) {
        if ((null != list) && (0 != list.size())) {

            int size = list.size();
            Object[] confirm = new Object[size - 1];

            String msg = (String) list.get(0);
            for (int i = 0; i < (size - 1); i++) {
                confirm[i] = list.get(i + 1);
            }

            alerts.add(ActionErrors.GLOBAL_ERROR, new ActionError(msg, confirm));
        }
    }


    /**
     * Check for pending message collection.
     * If it doesn't exist, and create is true, a new collection is
     * returned.
     *
     * @param request The HTTP request we are processing
     * @param create Whether to create a new collection if one does
     * not exist
     * @return The pending ActionMessages queue
     */
    protected ActionMessages getMessages(
            HttpServletRequest request,
            boolean create) {
        ActionErrors alerts = (ActionErrors) request.getAttribute(Globals.MESSAGE_KEY);

        if ((null == alerts) && (create)) {
            alerts = new ActionErrors();
            // Bypass Action.SaveMessage() since it
            // won't accept a blank collection
            request.setAttribute(Globals.MESSAGE_KEY, alerts);
        }

        return alerts;
    }


    /**
     * Merge incoming messages and save to messages
     * collection. The collection is automatically saved to the request,
     * so <code>Action.saveErrors(request,errors)</code> does not need
     * to be called. If this method called more than once, the new
     * messages are appended.
     * <p>
     * This method is upwardly compatabile with Struts 1.1 and uses the
     * messages queue introduced with that release.
     *
     * @param request The HTTP request we are processing
     * @param list The ResourceBundle token followed by 0 or more
     * parameters
     */
    protected void saveMessages(
            HttpServletRequest request,
            List list) {

        ActionMessages alerts = getMessages(request, true);
        mergeAlerts(request, alerts, list);

    }


    /**
     * Return whether there is an informational alert collection pending.
     *
     * @param request The HTTP request we are processing
     * @return True if an informational alert collection exists
     */
    protected boolean isMessages(HttpServletRequest request) {
        return (null != getMessages(request, false));
    }


    /**
     * Merge incoming messages and save to errors
     * collection. The collection is automatically saved to the request,
     * so <code>Action.saveErrors(request,errors)</code> does not need
     * to be called. If this method called more than once, the new
     * messages are appended.
     *
     * @param request The HTTP request we are processing
     * @param list The ResourceBundle token followed by 0 or more
     * parameters
     */
    protected void saveErrors(
            HttpServletRequest request,
            List list) {

        ActionErrors alerts = getErrors(request, true);
        mergeAlerts(request, alerts, list);

    }


    /**
     * Return whether there is an errors alert collection pending.
     *
     * @param request The HTTP request we are processing
     * @return True if an errors alert collection exists
     */
    protected ActionErrors getErrors(
            HttpServletRequest request,
            boolean create) {

        ActionErrors alerts = (ActionErrors)
                request.getAttribute(Globals.ERROR_KEY);

        if ((null == alerts) && (create)) {

            alerts = new ActionErrors();
            // Bypass Action.SaveError() since it
            // won't accept a blank collection
            request.setAttribute(Globals.ERROR_KEY, alerts);

        }

        return alerts;

    }


    /**
     * Return whether there is an errors alert collection pending.
     *
     * @param request The HTTP request we are processing
     * @return True if an errors alert collection exists
     */
    protected boolean isErrors(HttpServletRequest request) {
        ActionErrors errors = getErrors(request, false);
        return (errors != null && !errors.isEmpty());
    }


    /**
     * Optional extension point for pre-processing.
     * Default method does nothing.
     * To branch to another URI, return an non-null ActionForward.
     * If errors are logged (getErrors() et al),
     * default behaviour will branch to findFailure().
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request
     * @param request The HTTP request we are processing
     * @param response The resonse we are creating
     */
    protected void preProcess(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {

        // override to provide functionality

    }


    /**
     * Return the appropriate ActionForward for an
     * error condition.
     * The default method returns a forward to input,
     * when there is one, or "error" when not.
     * The application must provide an "error" forward.
     * An advanced implementation could check the errors
     * and provide different forwardings for different circumstances.
     * One possible error may be whether the form is null.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request
     * @param request The HTTP request we are processing
     * @param response The resonse we are creating
     * @return The ActionForward representing FAILURE
     * or null if a FAILURE forward has not been specified.
     */
    protected ActionForward findFailure(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {

        // If input page, use that
        if (mapping.getInput() != null)
            return (new ActionForward(mapping.getInput()));

        // If no input page, use error forwarding
        return mapping.findForward(FORWARD_FAILURE);

    }


    /**
     * Execute the business logic for this Action.
     * <p>
     * The default method logs the executeLogic() "event"
     * when the logging level is set to DEBUG.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request
     * @param request The HTTP request we are processing
     * @param response The resonse we are creating
     */
    protected void executeLogic(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // override to provide functionality, like
        // myBusinessObject.execute(form);

    }


    /**
     * Process the exception handling for this Action.
     *
     * Default behaviour should suffice for most circumstances.
     * If overridden, if an alert is logged to the errors
     * queue (getErrors()), then default behaviour will branch
     * to findFailure().
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request
     * @param request The HTTP request we are processing
     * @param response The response we are creating
     */
    protected void catchException(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {


        // Retrieve, log and print to error console
        Exception exception = getException(request);
        exception.printStackTrace();

        // General error message
        ActionErrors errors = getErrors(request, true);
        errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.general"));

        // Wrap exception messages in ActionError
        // If chained, descend down chain
        // appending messages to StringBuffer
        StringBuffer sb = new StringBuffer();
        sb.append(LINE_FEED);
        sb.append(ERROR);
        sb.append(exception.toString());
        String message = exception.getMessage();
        if (null != message) {
            sb.append(LINE_FEED);
            sb.append(DETAILS);
            sb.append(message);
            sb.append(LINE_FEED);
        }
        Throwable cause = exception.getCause();
        while(cause!=null) {
            sb.append(LINE_FEED);
            sb.append(CAUSED_BY);
            sb.append(LINE_FEED);
            sb.append(cause.toString());
            cause = cause.getCause();
        }

        errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.detail", sb.toString()));

    }


    protected WebAppEnvironmentInterface getEnvironment() {
        if(environment==null) {
            environment = WebAppEnvironmentPlugin.getEnvironment();
        }
        return environment;
    }

    /**
     * Optional extension point for post-processing.
     * Default method does nothing.
     * This is called from a finally{} clause,
     * and so is guaranteed to be called after executeLogic() or
     * catchException().
     * Use getException() to check if error occured.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request
     * @param request The HTTP request we are processing
     * @param response The resonse we are creating
     */
    protected void postProcess(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {

        // override to provide functionality

    }



    /**
     * Return the appropriate ActionForward for the nominal,
     * non-error state.
     * The default returns mapping.findForward("continue");
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request
     * @param request The HTTP request we are processing
     * @param response The response we are creating
     * @return The ActionForward representing SUCCESS
     * or null if a SUCCESS forward has not been specified.
     */
    protected ActionForward findSuccess(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {

        return mapping.findForward(FORWARD_SUCCESS);

    }

    /**
     * Replaces dynamic parameters in an ActionForward with real values
     * @param forward The ActionForward to update
     * @return The updated ActionForward, this is a new object if it has been updated or the same object if it has not
     * been updated
     */
    protected ActionForward replaceDynamicParameters(HttpServletRequest request, ActionForward forward) {
        if(forward!=null) {
            Map parameters = getDynamicParameters(request);
            if(parameters!=null) {
                String path = ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters);
                if(!path.equals(forward.getPath())) {
                    forward = new ActionForward(forward.getName(),path,forward.getRedirect(),forward.getContextRelative());
                }
            }
        }
        return forward;
    }

    /**
     * Get dynamic parameter values that can be replaced in action forward objects
     * @return A Map with all dynamic parameters
     */
    protected Map getDynamicParameters(HttpServletRequest request) {
        Map result = new HashMap();

        HttpSession session = request.getSession();
        if (session != null) {
            Enumeration attributes = session.getAttributeNames();
            while (attributes.hasMoreElements()) {
                String name = (String) attributes.nextElement();
                Object val = session.getAttribute(name);
                if (val != null) {
                    result.put(name, val);
                }
            }
        }

        Enumeration attributes = request.getAttributeNames();
        while (attributes.hasMoreElements()) {
            String name = (String) attributes.nextElement();
            Object val = request.getAttribute(name);
            if (val != null) {
                result.put(name, val);
            }
        }

        result.putAll(request.getParameterMap());
        return result;
    }


    /**
     * Skeleton method that calls the other extension points
     * (or "hotspots") provided by this class. These are:
     * <ul>
     * <li><code>preProcess(),</code></li>
     * <li><code>executeLogic(),</code></li>
     * <li><code>catchException(),</code></li>
     * <li><code>postProcess(),</code></li>
     * <li><code>findFailure(),</code></li>
     * <li><code>findSuccess()</code></li>
     * </ul>
     * Typically, you can just override the other extension points
     * as needed, and leave this one as is.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {


        // Check for precondition errors; fail if found
        preProcess(mapping, form, request, response);

        if (isErrors(request)) {
            return replaceDynamicParameters(request,findFailure(mapping, form, request, response));
        }

        // Try the logic; Call catchException() if needed
        try {

            executeLogic(mapping, form, request, response);
        } catch (Exception e) {
            // Store Exception; call extension point
            setException(request, e);
            catchException(mapping, form, request, response);
        } finally {
            postProcess(mapping, form, request, response);
        }

        // If errors queued, fail
        if (isErrors(request)) {
            return replaceDynamicParameters(request,findFailure(mapping, form, request, response));
        }

        return replaceDynamicParameters(request,findSuccess(mapping, form, request, response));
    }
}


