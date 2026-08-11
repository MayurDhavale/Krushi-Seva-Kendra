import 'package:flutter/material.dart';
import 'package:mobile/routes/app_routes.dart';
import 'package:mobile/screens/auth/login_screen.dart';
import 'package:mobile/screens/dashboard/dashboard_screen.dart';
import 'package:mobile/splash/splash_screen.dart';

class AppPages {

  static Map<String, WidgetBuilder> routes = {
        AppRoutes.splash: (context) => const SplashScreen(),
    AppRoutes.login: (context) => const LoginScreen(),
    AppRoutes.dashboard: (context) => const DashboardScreen(),
  };
}