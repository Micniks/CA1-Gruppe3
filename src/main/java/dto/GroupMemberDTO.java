package dto;

import entities.GroupMember;
import java.util.ArrayList;
import java.util.List;

public class GroupMemberDTO {

    private Long id;
    private String name;
    private String studentID;
    private String color;

    public GroupMemberDTO(GroupMember groupMember) {
        this.id = groupMember.getId();
        this.name = groupMember.getName();
        this.studentID = groupMember.getStudentID();
        this.color = groupMember.getColor();
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
    
    public static List<GroupMemberDTO> convertList(List<GroupMember> list){
        List<GroupMemberDTO> result = new ArrayList<GroupMemberDTO>();
        
        for (GroupMember groupMember : list) {
            result.add(new GroupMemberDTO(groupMember));
        }
        
        return result;
    }

}
