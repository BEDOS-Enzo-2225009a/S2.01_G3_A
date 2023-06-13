package com.example.s201_g3_a;



/**

 The {@code SisFranceViewModel} class represents the view model in the MVVM architectural pattern for the SisFrance application.

 It acts as an intermediary between the {@link SisFranceModel} and the view components, providing data and handling business logic.
 */
public class SisFranceViewModel {

    private SisFranceModel model;

    /**

     Sets the {@link SisFranceModel} for the view model.
     @param model the {@link SisFranceModel} to set
     */
    public void setModel(SisFranceModel model) {
        this.model = model;
    }
}

