package org.xzx.bean.ImageBean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageCQ {
    String file_name;
    String url;
    int file_size;
    long poster;
    long group_id;
}
