function submit(){

  let request = require('request');

  console.log("Submitted");

  let name = document.getElementById("name").value;
  let surname = document.getElementById("surname").value;
  let email = document.getElementById("name").value;
  let phone = document.getElementById("phone").value;

  const obj= {'msg': [
      {
        "name": name,
        "surname": surname,
        "email": email,
        "phone": phone
      },
    ]};

  request.post({
    url: 'localhost:8080/orders/new',
    body: obj,
    json: true
  }, function(error, response, body){
    console.log(body);
  });

}

