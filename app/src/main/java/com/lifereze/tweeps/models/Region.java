
package com.lifereze.tweeps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifereze.onlineshop.Center;

public class Region {

    @SerializedName("center")
    @Expose
    private com.lifereze.onlineshop.Center center;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Region() {
    }

    /**
     * 
     * @param center
     */
    public Region(com.lifereze.onlineshop.Center center) {
        super();
        this.center = center;
    }

    public com.lifereze.onlineshop.Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

}
