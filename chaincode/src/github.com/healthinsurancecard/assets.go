package main

type Card struct {
	CardId             string `json:"cardid"`
	FullName           string `json:"fullname"`
	DateOfBirth        string `json:"dateofbirth"`
	Address            string `json:"address"`
	RegisteredHospital string `json:"registerdhospital"`
	Code               string `json:"code"`
	ValidStartDate     string `json:"validstartdate"`
}
