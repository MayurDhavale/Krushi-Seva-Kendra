import 'package:flutter/material.dart';
import 'package:mobile/routes/app_pages.dart';
import 'package:mobile/splash/splash_screen.dart';

import 'core/theme/app_thems.dart' ;
import 'routes/app_routes.dart';
import 'screens/auth/login_screen.dart';

void main() {
  runApp(const KrushiSevaKendraApp());
}

class KrushiSevaKendraApp extends StatelessWidget {

  const KrushiSevaKendraApp({super.key});

  @override
  Widget build(BuildContext context) {
   return MaterialApp(
    debugShowCheckedModeBanner: false,
    theme: AppThems.lightTheme,

    initialRoute: AppRoutes.splash,
    routes : AppPages.routes

   // routes: {

      // AppRoutes.login : (_) => const LoginScreen(),
      // AppRoutes.splash : (_) => const SplashScreen(),
   // },
   );
  }
}


