
class LoginResponse {

  final String accessToken;
  final String tokenType;
  final String username;
  final String role;

  LoginResponse({
    required this.accessToken,
    required this.tokenType,
    required this.username,
    required this.role,
  });

  factory LoginResponse.fromJson(Map<String,dynamic> json){
    return LoginResponse(
      accessToken: json['accessToken'],
      tokenType: json['tokenType'],
      username: json['username'],
      role: json['role'],
      );
  }
}