package nju.zhizaolian.models;

/**
 * Created by lk on 15/3/3.
 */
public class Custom {
    private String imageUrl;
    private String name;
    private String phone;
    private String companyName;
    private String companyAddress;
    private String companyPhone;

    public Custom() {
    }

    public Custom(String imageUrl, String name, String phone, String companyName, String companyPhone, String companyAddress) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.phone = phone;
        this.companyName = companyName;
        this.companyPhone = companyPhone;
        this.companyAddress = companyAddress;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }
}
