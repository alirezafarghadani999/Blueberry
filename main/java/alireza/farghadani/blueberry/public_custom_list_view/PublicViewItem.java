package alireza.farghadani.blueberry.public_custom_list_view;

public class PublicViewItem {

    private int ID;
    private String label ;
    private String Description ;

    public PublicViewItem(String label, String description, int id) {
        this.label = label;
        this.Description = description;
        this.ID = id;
    }

    public String getLabel() {
        return label;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
