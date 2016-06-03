package web_services_connections_handlers;

import com.google.gson.Gson;

import reguest_parameters_pojos.GetChildrenParam;
import reguest_parameters_pojos.GetMainCategoriesParam;
import reguest_parameters_pojos.GetOffersParam;
import reguest_parameters_pojos.GetUserOffersParam;
import reguest_parameters_pojos.LogOutParam;
import reguest_parameters_pojos.SearchByOfferLocationParam;
import reguest_parameters_pojos.User;
import reguest_parameters_pojos.UserCheckParam;


public class JsonStringGenerator {

    public static String getUserCheckParam(String email,int registerationChannel) {

        UserCheckParam param=new UserCheckParam(email,registerationChannel);
        Gson gson = new Gson();
        String json = gson.toJson(param);
        return json;
    }
    public static String getGetUserOffersParam(int userId) {

        GetUserOffersParam param=new GetUserOffersParam();
        param.setUserId(userId);
        Gson gson = new Gson();
        String json = gson.toJson(param);
        return json;
    }

    public static String getremoveOfferParam() {

        return null;
    }

    public static String getSearchByOfferLocationParam(String location) {

        SearchByOfferLocationParam param=new SearchByOfferLocationParam();
        param.setLocation(location);
        Gson gson = new Gson();
        String json = gson.toJson(param);
        return json;

    }


    public static String getGetOffersParameters(int productId) {

        GetOffersParam param=new GetOffersParam();
        param.setProductId(productId);
        Gson gson = new Gson();
        String json = gson.toJson(param);
        return json;

    }

    public static String getGetChildrenParameters(String language,int categoryId) {

        GetChildrenParam param=new GetChildrenParam();
        param.setLanguage(language);
        param.setCategoryId(categoryId);
        Gson gson = new Gson();
        String json = gson.toJson(param);
        return json;

    }

    public static String getUpdateUserParameters(int userId,String fullName,String governorate,String email,String mobile,byte[] userImage) {

        User param =new User();

        param.setId(userId);
        param.setFullName(fullName);
        param.setGovernerate(governorate);
        param.setMail(email);
        param.setMobile(mobile);
        param.setLat(23.344);
        param.setLong_(234.32);
        param.setImage(userImage);

        Gson gson = new Gson();
        String json = gson.toJson(param);
        return json;
    }

    public static String getLogOutParameters(int userId) {
        LogOutParam param=new LogOutParam();
        param.setId(userId);
        Gson gson = new Gson();
        String json = gson.toJson(param);
        return json;
    }


    public static String getAddUserParameters(String fullName,String governorate,String email,String mobile,byte[] userImage){

        User param =new User();
        param.setFullName(fullName);
        param.setGovernerate(governorate);
        param.setMail(email);
        param.setMobile(mobile);
        param.setLat(23.344);
        param.setLong_(234.32);
        param.setImage(userImage);


        Gson gson = new Gson();
        String json = gson.toJson(param);
        return json;
    }

    public static String getMainCategoriesParameters(String language){

        GetMainCategoriesParam param=new GetMainCategoriesParam();
        param.setLanguage(language);
        Gson gson = new Gson();
        String json = gson.toJson(param);
        return json;
    }

}
