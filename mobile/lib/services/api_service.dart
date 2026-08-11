import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:mobile/core/constants/api_constants.dart';
import 'package:mobile/models/login_request.dart';
import 'package:mobile/models/login_response.dart';

class ApiService {

  Future<LoginResponse> login(LoginRequest request) async {

    final response = await http.post(
      Uri.parse("${ApiConstants.baseUrl}/auth/login"),

        headers: {
        "Content-Type": "application/json",
        },

      body: jsonEncode(
        request.toJson(),
      ),
    );



print("Status Code: ${response.statusCode}");
print("Response Body: ${response.body}");

    if(response.statusCode == 200){

      final json = jsonDecode(response.body);
      return LoginResponse.fromJson(
       json['data'],
      );
    }

    throw Exception(
      "Invalid UserName or password."
    );

  }
}