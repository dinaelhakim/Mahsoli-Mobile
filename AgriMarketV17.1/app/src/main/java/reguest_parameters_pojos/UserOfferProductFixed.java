package reguest_parameters_pojos;
// Generated Apr 29, 2016 4:07:51 PM by Hibernate Tools 4.3.1

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * UserOfferProductFixed generated by hbm2java
 */
public class UserOfferProductFixed implements java.io.Serializable {

    @Expose
    private Integer id;

    private Product product;

    private Unit unitByUnitId;

    private Unit unitByPricePerUnitId;

    private User user;

    @Expose
    private Date startDate;

    @Expose
    private float price;

    @Expose
    private float quantity;

    @Expose
    private String userPhone;

    @Expose
    private String userLocation;

    @Expose
    private Boolean recommended;
    
    @Expose
    private String imageUrl;

    @Expose
    private String description;
    private byte[] image;
 
    public UserOfferProductFixed() {
    }

    public UserOfferProductFixed(Product product, Unit unitByUnitId, Unit unitByPricePerUnitId, User user, Date startDate, float price, float quantity) {
        this.product = product;
        this.unitByUnitId = unitByUnitId;
        this.unitByPricePerUnitId = unitByPricePerUnitId;
        this.user = user;
        this.startDate = startDate;
        this.price = price;
        this.quantity = quantity;
    }

    public UserOfferProductFixed(Product product, Unit unitByUnitId, Unit unitByPricePerUnitId, User user, Date startDate, float price, float quantity, String userPhone, String userLocation, Boolean recommended, String imageUrl, String description) {
        this.product = product;
        this.unitByUnitId = unitByUnitId;
        this.unitByPricePerUnitId = unitByPricePerUnitId;
        this.user = user;
        this.startDate = startDate;
        this.price = price;
        this.quantity = quantity;
        this.userPhone = userPhone;
        this.userLocation = userLocation;
        this.recommended = recommended;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

  
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Unit getUnitByUnitId() {
        return this.unitByUnitId;
    }

    public void setUnitByUnitId(Unit unitByUnitId) {
        this.unitByUnitId = unitByUnitId;
    }

    public Unit getUnitByPricePerUnitId() {
        return this.unitByPricePerUnitId;
    }

    public void setUnitByPricePerUnitId(Unit unitByPricePerUnitId) {
        this.unitByPricePerUnitId = unitByPricePerUnitId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantity() {
        return this.quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getUserPhone() {
        return this.userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserLocation() {
        return this.userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public Boolean getRecommended() {
        return this.recommended;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserOfferProductFixed{" + "id=" + id + ", startDate=" + startDate + ", price=" + price + ", quantity=" + quantity + ", userPhone=" + userPhone + ", userLocation=" + userLocation + ", recommended=" + recommended + ", imageUrl=" + imageUrl + ", description=" + description + '}';
    }

}
