package org.acme.rest.client.multipart;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;


@Path("/client")
public class MultipartClientResource {
    private static final Logger LOG = Logger.getLogger(MultipartClientResource.class);
    @RestForm String firstName;
    @RestForm String lastName;
    @RestForm String email;



    @Inject
    @RestClient
    MultipartService service;


    // https://mkyong.com/webservices/jax-rs/file-upload-example-in-resteasy/
    private final String UPLOADED_FILE_PATH = ""; // will go to 'target' folder

    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    @Produces(MediaType.TEXT_HTML)





    public Response uploadFile(MultipartFormDataInput input)



    {
        String fileName = "";

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");

        for (InputPart inputPart : inputParts) {

            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);

                // convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                // constructs upload file path
                fileName = UPLOADED_FILE_PATH + fileName;

                writeFile(bytes, fileName);

                System.out.println("Done");

            } catch (IOException e) {
                e.printStackTrace();
            }




        }

        StringBuilder sb = new StringBuilder("");
        sb.append("<html>");
        sb.append("<head><title>Reporte de Persona</title></head>");
        sb.append("<body style=\"background-color:powderblue;\">");
        sb.append( "<h1>Reporte de persona</h1>");
        sb.append("<ul>");
        sb.append("<li>Datos Guardadados correctamente</li>");


        sb.append("</ul>");



        sb.append("</body>");
        sb.append("</html>");



        LOG.info("Se escribio el HTML");

        return Response.status(200).entity(sb.toString() +"Archivo guardado con nombre " + fileName).build();


    }

    /**
     * header sample { Content-Type=[image/png], Content-Disposition=[form-data;
     * name="file"; filename="filename.extension"] }
     **/
    // get uploaded filename, is there a easy way in RESTEasy?
    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    //save to somewhere
    public static void writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

    }
}