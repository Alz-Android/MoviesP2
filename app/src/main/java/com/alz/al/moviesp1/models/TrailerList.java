package com.alz.al.moviesp1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Al on 2/26/2016.
 */
public class TrailerList {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("results")
        @Expose
        public List<TrailerJSON> results = new ArrayList<TrailerJSON>();
}
