package com.myproject.project_oop.model;

import com.myproject.project_oop.model.constant.FileType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File extends BaseModel{


    @Column(name = "url")
    private String url;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private FileType type;

}
