package dk.grouptwo.model.objects;

public class Employer extends Account {
    private String CVR;
    private String companyName;
    private static final long serialVersionUID = 2;

    public Employer(String email, String phone, Address address, String CVR, String companyName) {
        super(email, phone, address);
        this.CVR = CVR;
        this.companyName = companyName;
    }

    public String getCVR() {
        return CVR;
    }

    public void setCVR(String CVR) {
        this.CVR = CVR;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean equals(Object obj) {
        if(!(obj instanceof Employer))
            return false;
        Employer other = (Employer) obj;
        return CVR.equals(other.CVR);
    }
}
