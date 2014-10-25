package com.aa.android.util;

import android.graphics.Bitmap;

/**
 * Created by darri_000 on 10/24/2014.
 */
public class ContactHolder
{
    private String name;
    private String number;
    private Bitmap optionalImage;

    public ContactHolder()
    {
    }

    public ContactHolder(String name, String number, Bitmap optionalImage)
    {
        this.name = name;
        this.number = number;
        this.optionalImage = optionalImage;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public Bitmap getOptionalImage()
    {
        return optionalImage;
    }

    public void setOptionalImage(Bitmap optionalImage)
    {
        this.optionalImage = optionalImage;
    }

    @Override
    public String toString()
    {
        return "ContactHolder{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", optionalImage=" + optionalImage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactHolder that = (ContactHolder) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (optionalImage != null ? !optionalImage.equals(that.optionalImage) : that.optionalImage != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (optionalImage != null ? optionalImage.hashCode() : 0);
        return result;
    }
}
