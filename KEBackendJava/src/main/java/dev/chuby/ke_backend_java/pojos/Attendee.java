package dev.chuby.ke_backend_java.pojos;


import com.google.gson.annotations.SerializedName;
import net.bytebuddy.build.ToStringPlugin;

import javax.persistence.*;

@Entity(name = "Attendees")
public class Attendee {

    // Attributes
    @Id
    @TableGenerator(name = "id_gen", initialValue = -1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_gen")
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    @SerializedName("last_name")
    private String lastName;

    @Column(nullable=false)
    private String avatar;

    @Column(nullable=false)
    private String about;

    // Constructor

    public Attendee() {

    }

    public Attendee(Long id, String name, String lastName, String avatar, String about) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.avatar = avatar;
        this.about = about;
    }

    // Modifiers
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", about='" + about + '\'' +
                '}';
    }
}
