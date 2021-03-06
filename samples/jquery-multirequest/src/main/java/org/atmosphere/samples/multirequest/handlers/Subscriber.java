/*
 * Copyright 2013 Jeanfrancois Arcand
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.atmosphere.samples.multirequest.handlers;

import org.apache.log4j.Logger;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;
import org.atmosphere.jersey.SuspendResponse;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/subscribe/{topic}")
@Produces("text/html;charset=ISO-8859-1")
public class Subscriber {

	private static final Logger LOG = Logger.getLogger(Subscriber.class);

	@PathParam("topic")
	private Broadcaster topic;

	@GET
	public SuspendResponse<String> subscribe() {
		LOG.debug("OnSubscribe to topic " + topic);
		SuspendResponse<String> sr = new SuspendResponse.SuspendResponseBuilder<String>().broadcaster(topic).outputComments(true)
				.build();
		return sr;
	}

	@POST
	@Broadcast
	public Broadcastable publish(@FormParam("message") String message) {
		LOG.debug("Receive message <" + message + ">, dispatch to other connected " + topic );
		return new Broadcastable(message, "", topic);
	}
}
