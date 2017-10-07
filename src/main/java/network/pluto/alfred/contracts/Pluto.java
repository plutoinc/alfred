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
    private static final String BINARY = "60606040525b60008054600160a060020a03191633600160a060020a03161790555b5b6104c4806100316000396000f300606060405263ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166357a7538e811461005e5780637a675bb6146100905780638da5cb5b146100c2578063f2fde38b146100f1575b600080fd5b341561006957600080fd5b610074600435610112565b604051600160a060020a03909116815260200160405180910390f35b341561009b57600080fd5b61007460043561012d565b604051600160a060020a03909116815260200160405180910390f35b34156100cd57600080fd5b6100746101e4565b604051600160a060020a03909116815260200160405180910390f35b34156100fc57600080fd5b610110600160a060020a03600435166101f3565b005b600160205260009081526040902054600160a060020a031681565b60008054819033600160a060020a0390811691161461014b57600080fd5b8261015461028c565b908152602001604051809103906000f080151561017057600080fd5b60008481526001602052604090819020805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03841690811790915591925084907f470292d5e9c87e6d5a100a14c56a434c9f25e9e2be1d5efa70c3b5f9aeda505f905160405180910390a38091505b5b50919050565b600054600160a060020a031681565b60005433600160a060020a0390811691161461020e57600080fd5b600160a060020a038116151561022357600080fd5b600054600160a060020a0380831691167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790555b5b50565b6040516101fc8061029d8339019056006060604052341561000f57600080fd5b6040516020806101fc833981016040528080519150505b5b60008054600160a060020a03191633600160a060020a03161790555b60018190555b505b6101a28061005a6000396000f300606060405263ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166365a617c781146100535780638da5cb5b14610078578063f2fde38b146100a7575b600080fd5b341561005e57600080fd5b6100666100c8565b60405190815260200160405180910390f35b341561008357600080fd5b61008b6100ce565b604051600160a060020a03909116815260200160405180910390f35b34156100b257600080fd5b6100c6600160a060020a03600435166100dd565b005b60015481565b600054600160a060020a031681565b60005433600160a060020a039081169116146100f857600080fd5b600160a060020a038116151561010d57600080fd5b600054600160a060020a0380831691167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790555b5b505600a165627a7a72305820d323f8f2a30f7d8d47314d97d999a4c1ee8c4d9a905b80ae669e5a371c3b22740029a165627a7a723058202addacde33bc2569d926ebc818e3fe3bd7e3ba73b04cab9556739aef8235ecdf0029";

    private Pluto(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Pluto(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<WalletCreatedEventResponse> getWalletCreatedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("WalletCreated", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<WalletCreatedEventResponse> responses = new ArrayList<WalletCreatedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            WalletCreatedEventResponse typedResponse = new WalletCreatedEventResponse();
            typedResponse.memberId = (Uint256) eventValues.getIndexedValues().get(0);
            typedResponse.walletAddr = (Address) eventValues.getIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WalletCreatedEventResponse> walletCreatedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("WalletCreated", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, WalletCreatedEventResponse>() {
            @Override
            public WalletCreatedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                WalletCreatedEventResponse typedResponse = new WalletCreatedEventResponse();
                typedResponse.memberId = (Uint256) eventValues.getIndexedValues().get(0);
                typedResponse.walletAddr = (Address) eventValues.getIndexedValues().get(1);
                return typedResponse;
            }
        });
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

    public Future<Address> mWallets(Uint256 param0) {
        Function function = new Function("mWallets", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
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

    public static class WalletCreatedEventResponse {
        public Uint256 memberId;

        public Address walletAddr;
    }

    public static class OwnershipTransferredEventResponse {
        public Address previousOwner;

        public Address newOwner;
    }
}
