package nju.zhizaolian.models;

import java.io.Serializable;

/**
 * Created by ColorfulCode on 2015/4/22.
 */
public class CustomerManagerInfo implements Serializable{
    private String user_name;
    private String user_password;
    private String customer_name;
    private String customer_phone;
    private String company_name;
    private String buy_contact;
    private String contact_phone_1;
    private String register_date;
    private String user_id="-1";

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getBuy_contact() {
        return buy_contact;
    }

    public void setBuy_contact(String buy_contact) {
        this.buy_contact = buy_contact;
    }

    public String getContact_phone_1() {
        return contact_phone_1;
    }

    public void setContact_phone_1(String contact_phone_1) {
        this.contact_phone_1 = contact_phone_1;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public static CustomerManagerInfo getCustomerManagerInfoFromAccountAndCustomer(Custom custom,Account account){
        if(custom==null||account==null){
            return  null;
        }else {
            CustomerManagerInfo info = new CustomerManagerInfo();
            info.user_id = ""+custom.getCustomerId();
            info.user_name = account.getUserName();
            info.user_password = account.getUserPassword();
            info.customer_name = custom.getCustomerName();
            info.customer_phone = custom.getCustomerPhone();
            info.company_name = custom.getCompanyName();
            info.buy_contact = custom.getBuyContact();
            info.contact_phone_1 = custom.getContactPhone1();
            //info.register_date=custom.getRegisterDate().toString();//缺少registerDate的解析
            return info;
        }

    }
}
