package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"strconv"

	"github.com/hyperledger/fabric/core/chaincode/shim"
	sc "github.com/hyperledger/fabric/protos/peer"
)

type SmartContract struct {
}

func (s *SmartContract) Init(APIstub shim.ChaincodeStubInterface) sc.Response {
	return shim.Success(nil)
}

func (s *SmartContract) Invoke(APIstub shim.ChaincodeStubInterface) sc.Response {

	function, args := APIstub.GetFunctionAndParameters()
	if function == "queryCard" {
		return s.queryCard(APIstub, args)
	} else if function == "initLedger" {
		return s.initLedger(APIstub)
	} else if function == "createCard" {
		return s.createCard(APIstub, args)
	} else if function == "queryAllCards" {
		return s.queryAllCards(APIstub)
	}

	return shim.Error("Invalid Smart Contract function name.")
}

func (s *SmartContract) queryCard(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	cardAsBytes, _ := APIstub.GetState(args[0])
	return shim.Success(cardAsBytes)
}

func (s *SmartContract) initLedger(APIstub shim.ChaincodeStubInterface) sc.Response {
	cards := []Card{
		Card{CardId: "0001", FullName: "Huy Nguyen", DateOfBirth: "1990-05-20", Address: "70/14 Nguyen Sy Sach", RegisteredHospital: "Tan Binh", Code: "000111", ValidStartDate: "2019-01-01"},
		Card{CardId: "0002", FullName: "Quoc Nguyen", DateOfBirth: "1996-08-17", Address: "70/14 Nguyen Sy Sach", RegisteredHospital: "Tan Binh", Code: "000111", ValidStartDate: "2019-01-01"},
	}

	i := 0
	for i < len(cards) {
		fmt.Println("i is ", i)
		cardAsBytes, _ := json.Marshal(cards[i])
		APIstub.PutState("CARD"+strconv.Itoa(i), cardAsBytes)
		fmt.Println("Added", cards[i])
		i = i + 1
	}

	return shim.Success(nil)
}

func (s *SmartContract) createCard(APIStub shim.ChaincodeStubInterface, args []string) sc.Response {

	if len(args) != 8 {
		return shim.Error("Incorrect number of arguments. Expecting 8")
	}

	var card = Card{CardId: args[1], FullName: args[2], DateOfBirth: args[3], Address: args[4], RegisteredHospital: args[5], Code: args[6], ValidStartDate: args[7]}

	cardAsBytes, _ := json.Marshal(card)
	APIStub.PutState(args[0], cardAsBytes)

	return shim.Success(nil)
}

func (s *SmartContract) queryAllCards(APIstub shim.ChaincodeStubInterface) sc.Response {

	startKey := "CARD0"
	endKey := "CARD999"

	resultsIterator, err := APIstub.GetStateByRange(startKey, endKey)
	if err != nil {
		return shim.Error(err.Error())
	}
	defer resultsIterator.Close()

	// buffer is a JSON array containing QueryResults
	var buffer bytes.Buffer
	buffer.WriteString("[")

	bArrayMemberAlreadyWritten := false
	for resultsIterator.HasNext() {
		queryResponse, err := resultsIterator.Next()
		if err != nil {
			return shim.Error(err.Error())
		}
		// Add a comma before array members, suppress it for the first array member
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString("{\"Key\":")
		buffer.WriteString("\"")
		buffer.WriteString(queryResponse.Key)
		buffer.WriteString("\"")

		buffer.WriteString(", \"Record\":")
		// Record is a JSON object, so we write as-is
		buffer.WriteString(string(queryResponse.Value))
		buffer.WriteString("}")
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString("]")

	fmt.Printf("- queryAllCards:\n%s\n", buffer.String())

	return shim.Success(buffer.Bytes())
}

// The main function is only relevant in unit test mode. Only included here for completeness.
func main() {

	// Create a new Smart Contract
	err := shim.Start(new(SmartContract))
	if err != nil {
		fmt.Printf("Error creating new Smart Contract: %s", err)
	}
}
