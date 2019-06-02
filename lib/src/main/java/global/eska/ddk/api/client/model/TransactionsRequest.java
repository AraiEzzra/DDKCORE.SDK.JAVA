package global.eska.ddk.api.client.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionsRequest {

    private Filter filter;
    private int limit;
    private int offset;
    private Sort[] sort;
}
