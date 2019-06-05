package global.eska.ddk.api.client.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sort {

    private String fieldName;
    private SortDirection sortDirection;
}
