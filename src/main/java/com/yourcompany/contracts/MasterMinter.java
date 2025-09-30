package com.yourcompany.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class MasterMinter extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b506040516115783803806115788339818101604052602081101561003357600080fd5b50518061003f33610065565b600280546001600160a01b0319166001600160a01b039290921691909117905550610087565b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6114e2806100966000396000f3fe608060405234801561001057600080fd5b50600436106100c95760003560e01c8063c011b1c311610081578063ea7215691161005b578063ea72156914610215578063f2fde38b1461021d578063f6a74ed714610250576100c9565b8063c011b1c31461018a578063c4faf7df146101bd578063cbf2b8bf146101f8576100c9565b80637c6b8ef5116100b25780637c6b8ef5146101345780638da5cb5b146101515780639398608b14610182576100c9565b806333db2ad2146100ce578063542fef91146100ff575b600080fd5b6100eb600480360360208110156100e457600080fd5b5035610283565b604080519115158252519081900360200190f35b6101326004803603602081101561011557600080fd5b503573ffffffffffffffffffffffffffffffffffffffff16610573565b005b6100eb6004803603602081101561014a57600080fd5b5035610687565b61015961098b565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b6101596109a7565b610159600480360360208110156101a057600080fd5b503573ffffffffffffffffffffffffffffffffffffffff166109c3565b610132600480360360408110156101d357600080fd5b5073ffffffffffffffffffffffffffffffffffffffff813581169160200135166109ee565b6100eb6004803603602081101561020e57600080fd5b5035610bc8565b6100eb610cb5565b6101326004803603602081101561023357600080fd5b503573ffffffffffffffffffffffffffffffffffffffff16610e2b565b6101326004803603602081101561026657600080fd5b503573ffffffffffffffffffffffffffffffffffffffff16610f7e565b3360009081526001602052604081205473ffffffffffffffffffffffffffffffffffffffff166102fe576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260358152602001806113dc6035913960400191505060405180910390fd5b60008211610357576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602a815260200180611411602a913960400191505060405180910390fd5b336000908152600160209081526040918290205460025483517faa271e1a00000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff928316600482018190529451929091169263aa271e1a92602480840193829003018186803b1580156103d957600080fd5b505afa1580156103ed573d6000803e3d6000fd5b505050506040513d602081101561040357600080fd5b505161045a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252603981526020018061143b6039913960400191505060405180910390fd5b600254604080517f8a6db9c300000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff848116600483015291516000939290921691638a6db9c391602480820192602092909190829003018186803b1580156104d157600080fd5b505afa1580156104e5573d6000803e3d6000fd5b505050506040513d60208110156104fb57600080fd5b50519050600061050b8286611161565b6040805187815260208101839052815192935073ffffffffffffffffffffffffffffffffffffffff86169233927f3703d23abba1e61f32acc0682fc062ea5c710672c7d100af5ecd08485e983ad0928290030190a361056a83826111d5565b95945050505050565b60005473ffffffffffffffffffffffffffffffffffffffff1633146105f957604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b60025460405173ffffffffffffffffffffffffffffffffffffffff8084169216907f9992ea32e96992be98be5c833cd5b9fd77314819d2146b6f06ab9cfef957af1290600090a3600280547fffffffffffffffffffffffff00000000000000000000000000000000000000001673ffffffffffffffffffffffffffffffffffffffff92909216919091179055565b3360009081526001602052604081205473ffffffffffffffffffffffffffffffffffffffff16610702576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260358152602001806113dc6035913960400191505060405180910390fd5b6000821161075b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602a815260200180611346602a913960400191505060405180910390fd5b336000908152600160209081526040918290205460025483517faa271e1a00000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff928316600482018190529451929091169263aa271e1a92602480840193829003018186803b1580156107dd57600080fd5b505afa1580156107f1573d6000803e3d6000fd5b505050506040513d602081101561080757600080fd5b505161085e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260398152602001806114746039913960400191505060405180910390fd5b600254604080517f8a6db9c300000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff848116600483015291516000939290921691638a6db9c391602480820192602092909190829003018186803b1580156108d557600080fd5b505afa1580156108e9573d6000803e3d6000fd5b505050506040513d60208110156108ff57600080fd5b5051905060008482116109125781610914565b845b905060006109228383611287565b6040805184815260208101839052815192935073ffffffffffffffffffffffffffffffffffffffff87169233927f3cc75d3bf58b0100659088c03539964108d5d06342e1bd8085ee43ad8ff6f69a928290030190a361098184826111d5565b9695505050505050565b60005473ffffffffffffffffffffffffffffffffffffffff1690565b60025473ffffffffffffffffffffffffffffffffffffffff1690565b73ffffffffffffffffffffffffffffffffffffffff9081166000908152600160205260409020541690565b60005473ffffffffffffffffffffffffffffffffffffffff163314610a7457604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b73ffffffffffffffffffffffffffffffffffffffff8216610ae0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806113706025913960400191505060405180910390fd5b73ffffffffffffffffffffffffffffffffffffffff8116610b4c576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260218152602001806113bb6021913960400191505060405180910390fd5b73ffffffffffffffffffffffffffffffffffffffff82811660008181526001602052604080822080547fffffffffffffffffffffffff0000000000000000000000000000000000000000169486169485179055517fa56687ff5096e83f6e2c673cda0b677f56bbfcdf5fe0555d5830c407ede193cb9190a35050565b3360009081526001602052604081205473ffffffffffffffffffffffffffffffffffffffff16610c43576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260358152602001806113dc6035913960400191505060405180910390fd5b33600081815260016020908152604091829020548251868152925173ffffffffffffffffffffffffffffffffffffffff90911693849390927f5b0b60a4f757b33d9dcb8bd021b6aa371bb2e6f134086797aefcd8c0afab538c92918290030190a3610cae81846111d5565b9392505050565b3360009081526001602052604081205473ffffffffffffffffffffffffffffffffffffffff16610d30576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260358152602001806113dc6035913960400191505060405180910390fd5b3360008181526001602052604080822054905173ffffffffffffffffffffffffffffffffffffffff90911692839290917f4b5ef9a786cf64a7d82ebcf2d5132667edc9faef4ac36260d9a9e52c526b62329190a3600254604080517f3092afd500000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff848116600483015291519190921691633092afd59160248083019260209291908290030181600087803b158015610df957600080fd5b505af1158015610e0d573d6000803e3d6000fd5b505050506040513d6020811015610e2357600080fd5b505191505090565b60005473ffffffffffffffffffffffffffffffffffffffff163314610eb157604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b73ffffffffffffffffffffffffffffffffffffffff8116610f1d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260268152602001806113956026913960400191505060405180910390fd5b6000546040805173ffffffffffffffffffffffffffffffffffffffff9283168152918316602083015280517f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09281900390910190a1610f7b816112fe565b50565b60005473ffffffffffffffffffffffffffffffffffffffff16331461100457604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b73ffffffffffffffffffffffffffffffffffffffff8116611070576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806113706025913960400191505060405180910390fd5b73ffffffffffffffffffffffffffffffffffffffff818116600090815260016020526040902054166110ed576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260218152602001806113bb6021913960400191505060405180910390fd5b73ffffffffffffffffffffffffffffffffffffffff811660008181526001602052604080822080547fffffffffffffffffffffffff0000000000000000000000000000000000000000169055517f33d83959be2573f5453b12eb9d43b3499bc57d96bd2f067ba44803c859e811139190a250565b600082820183811015610cae57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f770000000000604482015290519081900360640190fd5b600254604080517f4e44d95600000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff85811660048301526024820185905291516000939290921691634e44d9569160448082019260209290919082900301818787803b15801561125457600080fd5b505af1158015611268573d6000803e3d6000fd5b505050506040513d602081101561127e57600080fd5b50519392505050565b6000828211156112f857604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601e60248201527f536166654d6174683a207375627472616374696f6e206f766572666c6f770000604482015290519081900360640190fd5b50900390565b600080547fffffffffffffffffffffffff00000000000000000000000000000000000000001673ffffffffffffffffffffffffffffffffffffffff9290921691909117905556fe416c6c6f77616e63652064656372656d656e74206d7573742062652067726561746572207468616e2030436f6e74726f6c6c6572206d7573742062652061206e6f6e2d7a65726f20616464726573734f776e61626c653a206e6577206f776e657220697320746865207a65726f2061646472657373576f726b6572206d7573742062652061206e6f6e2d7a65726f20616464726573735468652076616c7565206f6620636f6e74726f6c6c6572735b6d73672e73656e6465725d206d757374206265206e6f6e2d7a65726f416c6c6f77616e636520696e6372656d656e74206d7573742062652067726561746572207468616e203043616e206f6e6c7920696e6372656d656e7420616c6c6f77616e636520666f72206d696e7465727320696e206d696e7465724d616e6167657243616e206f6e6c792064656372656d656e7420616c6c6f77616e636520666f72206d696e7465727320696e206d696e7465724d616e61676572a2646970667358221220fd5ce9639e2a336197c4d84d54c1626e0b95403c9f9b33da7dca012075bcb19764736f6c634300060c0033\n";

    private static String librariesLinkedBinary;

    public static final String FUNC_CONFIGURECONTROLLER = "configureController";

    public static final String FUNC_CONFIGUREMINTER = "configureMinter";

    public static final String FUNC_DECREMENTMINTERALLOWANCE = "decrementMinterAllowance";

    public static final String FUNC_GETMINTERMANAGER = "getMinterManager";

    public static final String FUNC_GETWORKER = "getWorker";

    public static final String FUNC_INCREMENTMINTERALLOWANCE = "incrementMinterAllowance";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REMOVECONTROLLER = "removeController";

    public static final String FUNC_REMOVEMINTER = "removeMinter";

    public static final String FUNC_SETMINTERMANAGER = "setMinterManager";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event CONTROLLERCONFIGURED_EVENT = new Event("ControllerConfigured", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event CONTROLLERREMOVED_EVENT = new Event("ControllerRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event MINTERALLOWANCEDECREMENTED_EVENT = new Event("MinterAllowanceDecremented", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event MINTERALLOWANCEINCREMENTED_EVENT = new Event("MinterAllowanceIncremented", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event MINTERCONFIGURED_EVENT = new Event("MinterConfigured", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event MINTERMANAGERSET_EVENT = new Event("MinterManagerSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event MINTERREMOVED_EVENT = new Event("MinterRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected MasterMinter(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MasterMinter(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MasterMinter(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MasterMinter(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ControllerConfiguredEventResponse> getControllerConfiguredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CONTROLLERCONFIGURED_EVENT, transactionReceipt);
        ArrayList<ControllerConfiguredEventResponse> responses = new ArrayList<ControllerConfiguredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ControllerConfiguredEventResponse typedResponse = new ControllerConfiguredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._controller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._worker = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ControllerConfiguredEventResponse getControllerConfiguredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CONTROLLERCONFIGURED_EVENT, log);
        ControllerConfiguredEventResponse typedResponse = new ControllerConfiguredEventResponse();
        typedResponse.log = log;
        typedResponse._controller = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse._worker = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ControllerConfiguredEventResponse> controllerConfiguredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getControllerConfiguredEventFromLog(log));
    }

    public Flowable<ControllerConfiguredEventResponse> controllerConfiguredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTROLLERCONFIGURED_EVENT));
        return controllerConfiguredEventFlowable(filter);
    }

    public static List<ControllerRemovedEventResponse> getControllerRemovedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CONTROLLERREMOVED_EVENT, transactionReceipt);
        ArrayList<ControllerRemovedEventResponse> responses = new ArrayList<ControllerRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ControllerRemovedEventResponse typedResponse = new ControllerRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._controller = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ControllerRemovedEventResponse getControllerRemovedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CONTROLLERREMOVED_EVENT, log);
        ControllerRemovedEventResponse typedResponse = new ControllerRemovedEventResponse();
        typedResponse.log = log;
        typedResponse._controller = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ControllerRemovedEventResponse> controllerRemovedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getControllerRemovedEventFromLog(log));
    }

    public Flowable<ControllerRemovedEventResponse> controllerRemovedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTROLLERREMOVED_EVENT));
        return controllerRemovedEventFlowable(filter);
    }

    public static List<MinterAllowanceDecrementedEventResponse> getMinterAllowanceDecrementedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MINTERALLOWANCEDECREMENTED_EVENT, transactionReceipt);
        ArrayList<MinterAllowanceDecrementedEventResponse> responses = new ArrayList<MinterAllowanceDecrementedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MinterAllowanceDecrementedEventResponse typedResponse = new MinterAllowanceDecrementedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.msgSender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.minter = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.decrement = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newAllowance = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MinterAllowanceDecrementedEventResponse getMinterAllowanceDecrementedEventFromLog(
            Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MINTERALLOWANCEDECREMENTED_EVENT, log);
        MinterAllowanceDecrementedEventResponse typedResponse = new MinterAllowanceDecrementedEventResponse();
        typedResponse.log = log;
        typedResponse.msgSender = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.minter = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.decrement = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.newAllowance = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<MinterAllowanceDecrementedEventResponse> minterAllowanceDecrementedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMinterAllowanceDecrementedEventFromLog(log));
    }

    public Flowable<MinterAllowanceDecrementedEventResponse> minterAllowanceDecrementedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINTERALLOWANCEDECREMENTED_EVENT));
        return minterAllowanceDecrementedEventFlowable(filter);
    }

    public static List<MinterAllowanceIncrementedEventResponse> getMinterAllowanceIncrementedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MINTERALLOWANCEINCREMENTED_EVENT, transactionReceipt);
        ArrayList<MinterAllowanceIncrementedEventResponse> responses = new ArrayList<MinterAllowanceIncrementedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MinterAllowanceIncrementedEventResponse typedResponse = new MinterAllowanceIncrementedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._msgSender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._minter = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._increment = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._newAllowance = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MinterAllowanceIncrementedEventResponse getMinterAllowanceIncrementedEventFromLog(
            Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MINTERALLOWANCEINCREMENTED_EVENT, log);
        MinterAllowanceIncrementedEventResponse typedResponse = new MinterAllowanceIncrementedEventResponse();
        typedResponse.log = log;
        typedResponse._msgSender = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse._minter = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse._increment = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse._newAllowance = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<MinterAllowanceIncrementedEventResponse> minterAllowanceIncrementedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMinterAllowanceIncrementedEventFromLog(log));
    }

    public Flowable<MinterAllowanceIncrementedEventResponse> minterAllowanceIncrementedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINTERALLOWANCEINCREMENTED_EVENT));
        return minterAllowanceIncrementedEventFlowable(filter);
    }

    public static List<MinterConfiguredEventResponse> getMinterConfiguredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MINTERCONFIGURED_EVENT, transactionReceipt);
        ArrayList<MinterConfiguredEventResponse> responses = new ArrayList<MinterConfiguredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MinterConfiguredEventResponse typedResponse = new MinterConfiguredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._msgSender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._minter = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._allowance = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MinterConfiguredEventResponse getMinterConfiguredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MINTERCONFIGURED_EVENT, log);
        MinterConfiguredEventResponse typedResponse = new MinterConfiguredEventResponse();
        typedResponse.log = log;
        typedResponse._msgSender = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse._minter = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse._allowance = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<MinterConfiguredEventResponse> minterConfiguredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMinterConfiguredEventFromLog(log));
    }

    public Flowable<MinterConfiguredEventResponse> minterConfiguredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINTERCONFIGURED_EVENT));
        return minterConfiguredEventFlowable(filter);
    }

    public static List<MinterManagerSetEventResponse> getMinterManagerSetEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MINTERMANAGERSET_EVENT, transactionReceipt);
        ArrayList<MinterManagerSetEventResponse> responses = new ArrayList<MinterManagerSetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MinterManagerSetEventResponse typedResponse = new MinterManagerSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._oldMinterManager = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._newMinterManager = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MinterManagerSetEventResponse getMinterManagerSetEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MINTERMANAGERSET_EVENT, log);
        MinterManagerSetEventResponse typedResponse = new MinterManagerSetEventResponse();
        typedResponse.log = log;
        typedResponse._oldMinterManager = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse._newMinterManager = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<MinterManagerSetEventResponse> minterManagerSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMinterManagerSetEventFromLog(log));
    }

    public Flowable<MinterManagerSetEventResponse> minterManagerSetEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINTERMANAGERSET_EVENT));
        return minterManagerSetEventFlowable(filter);
    }

    public static List<MinterRemovedEventResponse> getMinterRemovedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MINTERREMOVED_EVENT, transactionReceipt);
        ArrayList<MinterRemovedEventResponse> responses = new ArrayList<MinterRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MinterRemovedEventResponse typedResponse = new MinterRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._msgSender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._minter = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MinterRemovedEventResponse getMinterRemovedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MINTERREMOVED_EVENT, log);
        MinterRemovedEventResponse typedResponse = new MinterRemovedEventResponse();
        typedResponse.log = log;
        typedResponse._msgSender = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse._minter = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<MinterRemovedEventResponse> minterRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMinterRemovedEventFromLog(log));
    }

    public Flowable<MinterRemovedEventResponse> minterRemovedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINTERREMOVED_EVENT));
        return minterRemovedEventFlowable(filter);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> configureController(String _controller,
            String _worker) {
        final Function function = new Function(
                FUNC_CONFIGURECONTROLLER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _controller), 
                new org.web3j.abi.datatypes.Address(160, _worker)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> configureMinter(BigInteger _newAllowance) {
        final Function function = new Function(
                FUNC_CONFIGUREMINTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_newAllowance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> decrementMinterAllowance(
            BigInteger _allowanceDecrement) {
        final Function function = new Function(
                FUNC_DECREMENTMINTERALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_allowanceDecrement)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getMinterManager() {
        final Function function = new Function(FUNC_GETMINTERMANAGER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getWorker(String _controller) {
        final Function function = new Function(FUNC_GETWORKER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _controller)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> incrementMinterAllowance(
            BigInteger _allowanceIncrement) {
        final Function function = new Function(
                FUNC_INCREMENTMINTERALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_allowanceIncrement)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeController(String _controller) {
        final Function function = new Function(
                FUNC_REMOVECONTROLLER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _controller)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeMinter() {
        final Function function = new Function(
                FUNC_REMOVEMINTER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setMinterManager(String _newMinterManager) {
        final Function function = new Function(
                FUNC_SETMINTERMANAGER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _newMinterManager)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static MasterMinter load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new MasterMinter(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MasterMinter load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MasterMinter(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MasterMinter load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new MasterMinter(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MasterMinter load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MasterMinter(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<MasterMinter> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _minterManager) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _minterManager)));
        return deployRemoteCall(MasterMinter.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<MasterMinter> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            String _minterManager) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _minterManager)));
        return deployRemoteCall(MasterMinter.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<MasterMinter> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String _minterManager) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _minterManager)));
        return deployRemoteCall(MasterMinter.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<MasterMinter> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            String _minterManager) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _minterManager)));
        return deployRemoteCall(MasterMinter.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class ControllerConfiguredEventResponse extends BaseEventResponse {
        public String _controller;

        public String _worker;
    }

    public static class ControllerRemovedEventResponse extends BaseEventResponse {
        public String _controller;
    }

    public static class MinterAllowanceDecrementedEventResponse extends BaseEventResponse {
        public String msgSender;

        public String minter;

        public BigInteger decrement;

        public BigInteger newAllowance;
    }

    public static class MinterAllowanceIncrementedEventResponse extends BaseEventResponse {
        public String _msgSender;

        public String _minter;

        public BigInteger _increment;

        public BigInteger _newAllowance;
    }

    public static class MinterConfiguredEventResponse extends BaseEventResponse {
        public String _msgSender;

        public String _minter;

        public BigInteger _allowance;
    }

    public static class MinterManagerSetEventResponse extends BaseEventResponse {
        public String _oldMinterManager;

        public String _newMinterManager;
    }

    public static class MinterRemovedEventResponse extends BaseEventResponse {
        public String _msgSender;

        public String _minter;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
