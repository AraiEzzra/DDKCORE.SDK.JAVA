package global.eska.ddk.api.client.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionRequest {

    private Filter filter;
    private int limit;
    private int offset;
    private List<String> sort;
}
