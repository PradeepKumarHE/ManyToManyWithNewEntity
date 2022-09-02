# ManyToManyWithNewEntity

1. <strong>Add company with associated user (owner)</strong> <br />
    http://localhost:8080/api/company <br />
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
<br /><br />

2. <strong>Get companies and associated user where company status is 0</strong><br />
    Respose should be like below<br />
{
  "companyName":""
  "address":{}
  "assocoaiatedusers":[{}]
}

3. <strong>add user as employee</strong><br />
    Sample request :<br />
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

4. <strong>Add user  as consultant </strong>
    <br />Sample request : <br />
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
      
5.  <strong>Get user by userid</strong><br />
    Respose should be like below : <br />
    {
      "firstName":""
      "address":{}
      "assocoaiatedcompanies":[{}]
    }

      
      
      
