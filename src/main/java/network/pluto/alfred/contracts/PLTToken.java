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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
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
public final class PLTToken extends Contract {
    private static final String BINARY = "60606040526003805460a060020a61ffff02191690555b60038054600160a060020a03191633600160a060020a03161790555b5b610eb5806100426000396000f300606060405236156101045763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166305d2035b811461010957806306fdde0314610130578063095ea7b3146101bb57806318160ddd146101f157806323b872dd14610216578063313ce567146102525780633f4ba83a1461027b57806340c10f19146102905780635c975abb146102c657806366188463146102ed57806370a08231146103235780637d64bcb4146103545780638456cb591461037b5780638da5cb5b1461039057806395d89b41146103bf578063a9059cbb1461044a578063d73dd62314610480578063dd62ed3e146104b6578063f2fde38b146104ed575b600080fd5b341561011457600080fd5b61011c61050e565b604051901515815260200160405180910390f35b341561013b57600080fd5b61014361052f565b60405160208082528190810183818151815260200191508051906020019080838360005b838110156101805780820151818401525b602001610167565b50505050905090810190601f1680156101ad5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156101c657600080fd5b61011c600160a060020a0360043516602435610566565b604051901515815260200160405180910390f35b34156101fc57600080fd5b610204610594565b60405190815260200160405180910390f35b341561022157600080fd5b61011c600160a060020a036004358116906024351660443561059a565b604051901515815260200160405180910390f35b341561025d57600080fd5b6102656105ca565b60405160ff909116815260200160405180910390f35b341561028657600080fd5b61028e6105cf565b005b341561029b57600080fd5b61011c600160a060020a0360043516602435610652565b604051901515815260200160405180910390f35b34156102d157600080fd5b61011c610773565b604051901515815260200160405180910390f35b34156102f857600080fd5b61011c600160a060020a0360043516602435610783565b604051901515815260200160405180910390f35b341561032e57600080fd5b610204600160a060020a03600435166107b1565b60405190815260200160405180910390f35b341561035f57600080fd5b61011c6107d0565b604051901515815260200160405180910390f35b341561038657600080fd5b61028e610857565b005b341561039b57600080fd5b6103a36108df565b604051600160a060020a03909116815260200160405180910390f35b34156103ca57600080fd5b6101436108ee565b60405160208082528190810183818151815260200191508051906020019080838360005b838110156101805780820151818401525b602001610167565b50505050905090810190601f1680156101ad5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561045557600080fd5b61011c600160a060020a0360043516602435610925565b604051901515815260200160405180910390f35b341561048b57600080fd5b61011c600160a060020a0360043516602435610953565b604051901515815260200160405180910390f35b34156104c157600080fd5b610204600160a060020a0360043581169060243516610981565b60405190815260200160405180910390f35b34156104f857600080fd5b61028e600160a060020a03600435166109ae565b005b60035474010000000000000000000000000000000000000000900460ff1681565b60408051908101604052600581527f506c75746f000000000000000000000000000000000000000000000000000000602082015281565b60035460009060a860020a900460ff161561058057600080fd5b61058a8383610a47565b90505b5b92915050565b60005481565b60035460009060a860020a900460ff16156105b457600080fd5b6105bf848484610ab4565b90505b5b9392505050565b601281565b60035433600160a060020a039081169116146105ea57600080fd5b60035460a860020a900460ff16151561060257600080fd5b6003805475ff000000000000000000000000000000000000000000191690557f7805862f689e2f13df9f062ff482ad3ad112aca9e0847911ed832e158c525b3360405160405180910390a15b5b5b565b60035460009033600160a060020a0390811691161461067057600080fd5b60035474010000000000000000000000000000000000000000900460ff161561069857600080fd5b6000546106ab908363ffffffff610be016565b6000908155600160a060020a0384168152600160205260409020546106d6908363ffffffff610be016565b600160a060020a0384166000818152600160205260409081902092909255907f0f6798a560793a54c3bcfe86a93cde1e73087d944c0ea20544137d41213968859084905190815260200160405180910390a282600160a060020a031660007fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef8460405190815260200160405180910390a35060015b5b5b92915050565b60035460a860020a900460ff1681565b60035460009060a860020a900460ff161561079d57600080fd5b61058a8383610bfa565b90505b5b92915050565b600160a060020a0381166000908152600160205260409020545b919050565b60035460009033600160a060020a039081169116146107ee57600080fd5b6003805474ff00000000000000000000000000000000000000001916740100000000000000000000000000000000000000001790557fae5184fba832cb2b1f702aca6117b8d265eaf03ad33eb133f19dde0f5920fa0860405160405180910390a15060015b5b90565b60035433600160a060020a0390811691161461087257600080fd5b60035460a860020a900460ff161561088957600080fd5b6003805475ff000000000000000000000000000000000000000000191660a860020a1790557f6985a02210a168e66602d3235cb6db0e70f92b3ba4d376a33c0f3d9434bff62560405160405180910390a15b5b5b565b600354600160a060020a031681565b60408051908101604052600381527f504c540000000000000000000000000000000000000000000000000000000000602082015281565b60035460009060a860020a900460ff161561093f57600080fd5b61058a8383610cf6565b90505b5b92915050565b60035460009060a860020a900460ff161561096d57600080fd5b61058a8383610dcd565b90505b5b92915050565b600160a060020a038083166000908152600260209081526040808320938516835292905220545b92915050565b60035433600160a060020a039081169116146109c957600080fd5b600160a060020a03811615156109de57600080fd5b600354600160a060020a0380831691167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a36003805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790555b5b50565b600160a060020a03338116600081815260026020908152604080832094871680845294909152808220859055909291907f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259085905190815260200160405180910390a35060015b92915050565b600080600160a060020a0384161515610acc57600080fd5b50600160a060020a03808516600081815260026020908152604080832033909516835293815283822054928252600190529190912054610b12908463ffffffff610e7216565b600160a060020a038087166000908152600160205260408082209390935590861681522054610b47908463ffffffff610be016565b600160a060020a038516600090815260016020526040902055610b70818463ffffffff610e7216565b600160a060020a03808716600081815260026020908152604080832033861684529091529081902093909355908616917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9086905190815260200160405180910390a3600191505b509392505050565b600082820183811015610bef57fe5b8091505b5092915050565b600160a060020a03338116600090815260026020908152604080832093861683529290529081205480831115610c5757600160a060020a033381166000908152600260209081526040808320938816835292905290812055610c8e565b610c67818463ffffffff610e7216565b600160a060020a033381166000908152600260209081526040808320938916835292905220555b600160a060020a0333811660008181526002602090815260408083209489168084529490915290819020547f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925915190815260200160405180910390a3600191505b5092915050565b6000600160a060020a0383161515610d0d57600080fd5b600160a060020a033316600090815260016020526040902054610d36908363ffffffff610e7216565b600160a060020a033381166000908152600160205260408082209390935590851681522054610d6b908363ffffffff610be016565b600160a060020a0380851660008181526001602052604090819020939093559133909116907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9085905190815260200160405180910390a35060015b92915050565b600160a060020a033381166000908152600260209081526040808320938616835292905290812054610e05908363ffffffff610be016565b600160a060020a0333811660008181526002602090815260408083209489168084529490915290819020849055919290917f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92591905190815260200160405180910390a35060015b92915050565b600082821115610e7e57fe5b508082035b929150505600a165627a7a72305820b26f3a55572995617ee2623f7042b5375f0938c9ac37a6b6bf960e69bd32607e0029";

    private PLTToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private PLTToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<PauseEventResponse> getPauseEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Pause", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<PauseEventResponse> responses = new ArrayList<PauseEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            PauseEventResponse typedResponse = new PauseEventResponse();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<PauseEventResponse> pauseEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Pause", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, PauseEventResponse>() {
            @Override
            public PauseEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                PauseEventResponse typedResponse = new PauseEventResponse();
                return typedResponse;
            }
        });
    }

    public List<UnpauseEventResponse> getUnpauseEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Unpause", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<UnpauseEventResponse> responses = new ArrayList<UnpauseEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            UnpauseEventResponse typedResponse = new UnpauseEventResponse();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<UnpauseEventResponse> unpauseEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Unpause", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, UnpauseEventResponse>() {
            @Override
            public UnpauseEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                UnpauseEventResponse typedResponse = new UnpauseEventResponse();
                return typedResponse;
            }
        });
    }

    public List<MintEventResponse> getMintEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Mint", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<MintEventResponse> responses = new ArrayList<MintEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            MintEventResponse typedResponse = new MintEventResponse();
            typedResponse.to = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<MintEventResponse> mintEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Mint", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, MintEventResponse>() {
            @Override
            public MintEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                MintEventResponse typedResponse = new MintEventResponse();
                typedResponse.to = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public List<MintFinishedEventResponse> getMintFinishedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("MintFinished", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<MintFinishedEventResponse> responses = new ArrayList<MintFinishedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            MintFinishedEventResponse typedResponse = new MintFinishedEventResponse();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<MintFinishedEventResponse> mintFinishedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("MintFinished", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, MintFinishedEventResponse>() {
            @Override
            public MintFinishedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                MintFinishedEventResponse typedResponse = new MintFinishedEventResponse();
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

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Approval", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.owner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.spender = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Approval", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.owner = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.spender = (Address) eventValues.getIndexedValues().get(1);
                typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Transfer", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.from = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.to = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Transfer", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.from = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.to = (Address) eventValues.getIndexedValues().get(1);
                typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Future<Bool> mintingFinished() {
        Function function = new Function("mintingFinished", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Utf8String> name() {
        Function function = new Function("name", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> approve(Address _spender, Uint256 _value) {
        Function function = new Function("approve", Arrays.<Type>asList(_spender, _value), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint256> totalSupply() {
        Function function = new Function("totalSupply", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> transferFrom(Address _from, Address _to, Uint256 _value) {
        Function function = new Function("transferFrom", Arrays.<Type>asList(_from, _to, _value), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint8> decimals() {
        Function function = new Function("decimals", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> unpause() {
        Function function = new Function("unpause", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> mint(Address _to, Uint256 _amount) {
        Function function = new Function("mint", Arrays.<Type>asList(_to, _amount), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Bool> paused() {
        Function function = new Function("paused", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> decreaseApproval(Address _spender, Uint256 _subtractedValue) {
        Function function = new Function("decreaseApproval", Arrays.<Type>asList(_spender, _subtractedValue), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint256> balanceOf(Address _owner) {
        Function function = new Function("balanceOf", 
                Arrays.<Type>asList(_owner), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> finishMinting() {
        Function function = new Function("finishMinting", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> pause() {
        Function function = new Function("pause", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Address> owner() {
        Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Utf8String> symbol() {
        Function function = new Function("symbol", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> transfer(Address _to, Uint256 _value) {
        Function function = new Function("transfer", Arrays.<Type>asList(_to, _value), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> increaseApproval(Address _spender, Uint256 _addedValue) {
        Function function = new Function("increaseApproval", Arrays.<Type>asList(_spender, _addedValue), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint256> allowance(Address _owner, Address _spender) {
        Function function = new Function("allowance", 
                Arrays.<Type>asList(_owner, _spender), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> transferOwnership(Address newOwner) {
        Function function = new Function("transferOwnership", Arrays.<Type>asList(newOwner), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<PLTToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(PLTToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<PLTToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(PLTToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static PLTToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PLTToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static PLTToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PLTToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class PauseEventResponse {
    }

    public static class UnpauseEventResponse {
    }

    public static class MintEventResponse {
        public Address to;

        public Uint256 amount;
    }

    public static class MintFinishedEventResponse {
    }

    public static class OwnershipTransferredEventResponse {
        public Address previousOwner;

        public Address newOwner;
    }

    public static class ApprovalEventResponse {
        public Address owner;

        public Address spender;

        public Uint256 value;
    }

    public static class TransferEventResponse {
        public Address from;

        public Address to;

        public Uint256 value;
    }
}
