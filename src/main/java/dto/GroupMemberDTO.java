package dto;

import entities.GroupMember;

public class GroupMemberDTO {

    private Long id;
    private String name;
    private String studentID;
    private String color;

    public GroupMemberDTO(GroupMember groupMember) {
        this.id = id;
        this.name = name;
        this.studentID = studentID;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getColor() {
        return color;
    }

}
