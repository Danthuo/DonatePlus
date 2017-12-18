package bbitb.com.donateplus;

/**
 * Created by Avraj Singh on 17/12/2017.
 */

public class donation {
    public int donationID;
    public String profileName;
    public String donationType;
    public int donationAmount;
    public String dropOffLoc;
    public String donationStatus;

    public donation(int donationID, String profileName, String donationType, int donationAmount, String dropOffLoc, String donationStatus){
        this.donationID = donationID;
        this.profileName = profileName;
        this.donationType = donationType;
        this.donationAmount = donationAmount;
        this.dropOffLoc = dropOffLoc;
        this.donationStatus = donationStatus;
    }

    public String getDonationID(){
        return String.valueOf(donationID);
    }

    public String getProfileName(){
        return profileName;
    }

    public String getDonationType(){
        return donationType;
    }

    public String getDonationAmount(){
        return String.valueOf(donationAmount);
    }

    public String getDropOffLoc(){
        return dropOffLoc;
    }

    public String getDonationStatus(){
        return donationStatus;
    }
}
