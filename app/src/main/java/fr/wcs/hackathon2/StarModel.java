package fr.wcs.hackathon2;

public class StarModel {

    private int id;
    private double height;
    private int mass;
    private String name;
    private String gender;
    private String homeworld;
    private String species;
    private String image;
    private String haircolor;
    private String eyecolor;
    private String skincolor;
    private String side;

    public StarModel(int id, double height, int mass, String name, String gender, String homeworld, String species, String image, String haircolor, String eyecolor, String skincolor, String side) {
        this.id = id;
        this.height = height;
        this.mass = mass;
        this.name = name;
        this.gender = gender;
        this.homeworld = homeworld;
        this.species = species;
        this.image = image;
        this.haircolor = haircolor;
        this.eyecolor = eyecolor;
        this.skincolor = skincolor;
        this.side = side;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHaircolor() {
        return haircolor;
    }

    public void setHaircolor(String haircolor) {
        this.haircolor = haircolor;
    }

    public String getEyecolor() {
        return eyecolor;
    }

    public void setEyecolor(String eyecolor) {
        this.eyecolor = eyecolor;
    }

    public String getSkincolor() {
        return skincolor;
    }

    public void setSkincolor(String skincolor) {
        this.skincolor = skincolor;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
