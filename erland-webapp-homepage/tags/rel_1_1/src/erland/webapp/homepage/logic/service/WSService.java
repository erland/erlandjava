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
                inParameters.put(token,parameters.getParameter(token));
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
                    LOG.debug("Set parameter "+name+"="+value);
                    input.setObjectPart(name.toString(),value);
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