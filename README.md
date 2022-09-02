# ManyToManyWithNewEntity

1. <strong>Add company with associated user</strong> (owner)<br />
    http://localhost:8080/api/company
    Request 
    {
    "company": {
        "companyName": "ABC",
        "companyTypeID": 1,
        "companyWebsite": "www.abc.com",
        "numberOfEmployeesID": 1,
        "address": {
            "countryId": 1,
            "stateID": 1,
            "cityID": 1,
            "zipCode": 1234,
            "street": "company address",
            "phoneCountryID": 1,
            "phone": 316986,
            "phoneExtension": 123
        }
    },
    "user": {
        "firstName": "pradeep",
        "lastName": "kumar",
        "email": "pradeep@gmail.com",
        "address": {
            "phoneCountryId": 1,
            "phone": 9964289813
        }
    }
}

2. Get companies and associated user where company status is 0
{
  "companyName":""
  "address":{}
  "assocoaiatedusers":[{}]
}

3. add user as employee
        {
          "company": {
            "companyId": 1
          },
          "user": {
            "firstName": ""
          },
          "designation": "",
          "role": "",
          "worklocaction": {
            "addressId": 1
          }
        }

4. add user  as consultant
      {
        "company": {
          "companyId": 1
        },
        "user": {
          "firstName": ""
        },
        "designation": "",
        "role": "",
        "externalCompany": {"externalCompanyId":1},
        "externalCompanyAddress": {"externalCompanyAddressId":1}
      }
5 get user by userid

{
  "firstName":""
  "address":{}
  "assocoaiatedcompanies":[{}]
}
