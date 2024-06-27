package middlewares;

import com.google.common.hash.Hashing;
import spark.Request;
import spark.Response;

import java.nio.charset.StandardCharsets;

public class AuthMiddleware {

    public static Response  verificarSesion(Request request, Response response){
        if(request.session().isNew() || request.session().attribute("id") == null){
            response.redirect("/login");
        }
        return response;
    }






    /*
    public String getHashPassword(String password){
        String sha256hex = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        return sha256hex;
    }
     */
}
