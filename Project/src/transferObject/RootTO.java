//Made By F219491
package transferObject;

public class RootTO {

    private String root_Name;
    private String status;

    public RootTO(String root_Name, String status) {
        this.root_Name = root_Name;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getRootName() {
        return root_Name;
    }

    public void setRoot_Name(String root_Name) {
        this.root_Name = root_Name;
    }

}
