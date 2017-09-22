package network.pluto.alfred.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.3.0.
 */
public final class Pluto extends Contract {
    private static final String BINARY = "60606040525b60008054600160a060020a03191633600160a060020a03161790555b5b6103fd806100316000396000f300606060405263ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416637a675bb681146100535780638da5cb5b1461006b578063f2fde38b1461009a575b600080fd5b341561005e57600080fd5b6100696004356100bb565b005b341561007657600080fd5b61007e61011d565b604051600160a060020a03909116815260200160405180910390f35b34156100a557600080fd5b610069600160a060020a036004351661012c565b005b806100c46101c5565b908152602001604051809103906000f08015156100e057600080fd5b6000828152600160205260409020805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03929092169190911790555b50565b600054600160a060020a031681565b60005433600160a060020a0390811691161461014757600080fd5b600160a060020a038116151561015c57600080fd5b600054600160a060020a0380831691167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790555b5b50565b6040516101fc806101d68339019056006060604052341561000f57600080fd5b6040516020806101fc833981016040528080519150505b5b60008054600160a060020a03191633600160a060020a03161790555b60018190555b505b6101a28061005a6000396000f300606060405263ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166365a617c781146100535780638da5cb5b14610078578063f2fde38b146100a7575b600080fd5b341561005e57600080fd5b6100666100c8565b60405190815260200160405180910390f35b341561008357600080fd5b61008b6100ce565b604051600160a060020a03909116815260200160405180910390f35b34156100b257600080fd5b6100c6600160a060020a03600435166100dd565b005b60015481565b600054600160a060020a031681565b60005433600160a060020a039081169116146100f857600080fd5b600160a060020a038116151561010d57600080fd5b600054600160a060020a0380831691167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790555b5b505600a165627a7a723058206152f65ab9511392d60cae3d65ec322fa86a8b06f2ab72337d44834399ade6250029a165627a7a723058204f0fcda24c3454c876f0c307ad2241f69208ac4a39bf00468bf34ef67f44896d0029";

    private Pluto(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Pluto(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("OwnershipTransferred", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.previousOwner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.newOwner = (Address) eventValues.getIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("OwnershipTransferred", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.previousOwner = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.newOwner = (Address) eventValues.getIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Future<TransactionReceipt> createWallet(Uint256 _memberId) {
        Function function = new Function("createWallet", Arrays.<Type>asList(_memberId), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Address> owner() {
        Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> transferOwnership(Address newOwner) {
        Function function = new Function("transferOwnership", Arrays.<Type>asList(newOwner), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<Pluto> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Pluto.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<Pluto> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Pluto.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Pluto load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Pluto(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Pluto load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Pluto(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class OwnershipTransferredEventResponse {
        public Address previousOwner;

        public Address newOwner;
    }
}
