package global.eska.ddk.api.client.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Block {

    private String id;
    private Long version;
    private Long createdAt;
    private Long height;
    private String previousBlockId;
    private Integer transactionCount;
    private Long amount;
    private Long fee;
    private String payloadHash;
    private String generatorPublicKey;
    private String signature;
    private Integer relay;
    private List<Transaction> transactions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getPreviousBlockId() {
        return previousBlockId;
    }

    public void setPreviousBlockId(String previousBlockId) {
        this.previousBlockId = previousBlockId;
    }

    public Integer getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(Integer transactionCount) {
        this.transactionCount = transactionCount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getPayloadHash() {
        return payloadHash;
    }

    public void setPayloadHash(String payloadHash) {
        this.payloadHash = payloadHash;
    }

    public String getGeneratorPublicKey() {
        return generatorPublicKey;
    }

    public void setGeneratorPublicKey(String generatorPublicKey) {
        this.generatorPublicKey = generatorPublicKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getRelay() {
        return relay;
    }

    public void setRelay(Integer relay) {
        this.relay = relay;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
