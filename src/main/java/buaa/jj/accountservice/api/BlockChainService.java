package buaa.jj.accountservice.api;

public interface BlockChainService {
    String QueryTransaction(int recordId);
    String QueryBalanceChange(int recordId);
    boolean InsertTransaction(
            int recordId,
            int paymentInstitutionId,
            int paymentUserId,
            int collectionInstitutionId,
            int collectionUserId,
            String dateTime,
            boolean transactionType,
            double sum

    );
    boolean InsertBalanceChange(
            int recordId,
            int institutionId,
            int userId,
            String dateTime,
            boolean recordType,
            double sum
    );
}
