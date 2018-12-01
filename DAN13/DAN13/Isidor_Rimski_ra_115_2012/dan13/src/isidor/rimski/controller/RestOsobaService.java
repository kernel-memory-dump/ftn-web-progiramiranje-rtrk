package isidor.rimski.controller;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import isidor.rimski.dto.OsobaDTO;

@Path("/osobe/")
public class RestOsobaService {



	public class JSONService {

		@GET
		@Path("/getListOsoba")
		@Produces(MediaType.APPLICATION_JSON)
		public List<OsobaDTO> getTrackInJSON() {

			Track track = new Track();
			track.setTitle("Enter Sandman");
			track.setSinger("Metallica");

			return track;

		}

		@POST
		@Path("/post")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response createTrackInJSON(Track track) {

			String result = "Track saved : " + track;
			return Response.status(201).entity(result).build();

		}

	
}
