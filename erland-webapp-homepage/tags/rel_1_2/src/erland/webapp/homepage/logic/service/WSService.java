package erland.webapp.homepage.logic.service;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.wsif.*;
import org.apache.wsif.util.WSIFUtils;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import java.util.StringTokenizer;

import erland.util.ParameterValueStorageInterface;
import erland.util.StringUtil;

public class WSService implements ServiceInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(WSService.class);

    public String execute(ParameterValueStorageInterface parameters) {
        Map inParameters = new HashMap();
        String in = parameters.getParameter("in");
        if(in!=null) {
            StringTokenizer tokens = new StringTokenizer(in," ");
            while(tokens.hasMoreElements()) {
                String token = (String) tokens.nextElement();
                int typeSeparator = token.indexOf(':');
                if(typeSeparator>=0) {
                    inParameters.put(token,parameters.getParameter(token.substring(0,typeSeparator)));
                }else {
                    inParameters.put(token,parameters.getParameter(token));
                }
            }
        }
        return execute(parameters.getParameter("wsdl"),
                parameters.getParameter("operation"),
                parameters.getParameter("port"),
                inParameters,
                parameters.getParameter("out"));
    }

    protected String execute(
        String wsdl,
        String operationName,
        String portName,
        Map inParameters,
        String outParameter) {

        String result = null;
        try {
            LOG.debug("Reading WSDL document: " + wsdl);
            Definition def = WSIFUtils.readWSDL(null, wsdl);

            LOG.debug("Preparing WSIF invocation");
            WSIFServiceFactory factory = WSIFServiceFactory.newInstance();
            WSIFService service = factory.getService(def, null, null, def.getTargetNamespace(), portName);
            WSIFPort port = service.getPort();

            WSIFOperation operation =
                port.createOperation(operationName);
            WSIFMessage input = operation.createInputMessage();
            WSIFMessage output = operation.createOutputMessage();
            WSIFMessage fault = operation.createFaultMessage();

            for (Iterator it = inParameters.keySet().iterator(); it.hasNext(); ) {
                Object name = it.next();
                Object value = inParameters.get(name);
                if(value!=null) {
                    String nameString = name.toString();
                    int typeSeparator = nameString.indexOf(':');
                    if(typeSeparator>=0) {
                        String type = nameString.substring(typeSeparator+1);
                        nameString = nameString.substring(0,typeSeparator);
                        if(type.equalsIgnoreCase("int")) {
                            int intValue = StringUtil.asInteger(value.toString(),new Integer(0)).intValue();
                            LOG.debug("Set int parameter "+nameString+"="+intValue);
                            input.setIntPart(nameString,intValue);
                        }else if(type.equalsIgnoreCase("float")) {
                            float floatValue = StringUtil.asFloat(value.toString(),new Float(0)).floatValue();
                            LOG.debug("Set float parameter "+nameString+"="+floatValue);
                            input.setFloatPart(nameString,floatValue);
                        }else if(type.equalsIgnoreCase("double")) {
                            double doubleValue = StringUtil.asDouble(value.toString(),new Double(0)).doubleValue();
                            LOG.debug("Set double parameter "+nameString+"="+doubleValue);
                            input.setDoublePart(nameString,doubleValue);
                        }else {
                            LOG.debug("Set Object parameter "+nameString+"="+value);
                            input.setObjectPart(nameString,value);
                        }
                    }else {
                        LOG.debug("Set Object parameter "+name+"="+value);
                        input.setObjectPart(name.toString(),value);
                    }
                }
            }

            LOG.debug("Executing operation " + operationName);
            if(operation.executeRequestResponseOperation(input, output, fault)) {
                Object res = output.getObjectPart(outParameter);
                if(res instanceof Object[]) {
                    StringBuffer sb = new StringBuffer();
                    Object[] resStrings = (Object[]) res;
                    for (int i = 0; i < resStrings.length; i++) {
                        sb.append(resStrings[i]);
                        sb.append("\n");
                    }
                    result = sb.toString();
                }else if(res instanceof Object) {
                    result = res.toString();
                }
            }else {
                Object error = fault.getObjectPart(WSIFConstants.SOAP_FAULT_OBJECT);
                LOG.info("WebService error: "+error);
            }
        } catch (WSDLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (WSIFException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        LOG.debug("Got: "+result);
        return result;
    }
}