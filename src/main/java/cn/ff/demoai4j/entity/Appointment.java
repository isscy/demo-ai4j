package cn.ff.demoai4j.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {


    private Long id;

    private String username;

    private String idCard;

    private String department;

    private String date;

    private String time;

    private String doctorName;
}
