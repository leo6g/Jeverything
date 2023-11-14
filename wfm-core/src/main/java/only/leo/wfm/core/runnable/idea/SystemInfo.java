package only.leo.wfm.core.runnable.idea;

import java.io.File;

/**
 * Provides information about operating system, system-wide settings, and Java Runtime.
 */
public final class SystemInfo {
  public static final String OS_NAME = SystemInfoRt.OS_NAME;
  public static final String OS_VERSION = SystemInfoRt.OS_VERSION;
  public static final String OS_ARCH = System.getProperty("os.arch");
  public static final String JAVA_VERSION = System.getProperty("java.version");
  public static final String JAVA_RUNTIME_VERSION = getRtVersion(JAVA_VERSION);
  public static final String JAVA_VENDOR = System.getProperty("java.vm.vendor", "Unknown");

  public static final boolean isAarch64 = OS_ARCH.equals("aarch64");

  private static String getRtVersion(@SuppressWarnings("SameParameterValue") String fallback) {
    String rtVersion = System.getProperty("java.runtime.version");
    return rtVersion != null && Character.isDigit(rtVersion.charAt(0)) ? rtVersion : fallback;
  }

  public static final boolean isWindows = SystemInfoRt.isWindows;
  public static final boolean isMac = SystemInfoRt.isMac;
  public static final boolean isLinux = SystemInfoRt.isLinux;
  public static final boolean isFreeBSD = SystemInfoRt.isFreeBSD;
  public static final boolean isSolaris = SystemInfoRt.isSolaris;
  public static final boolean isUnix = SystemInfoRt.isUnix;

  public static final boolean isChromeOS = isLinux && isCrostini();
  public static final boolean isOracleJvm = JAVA_VENDOR.indexOf("Oracle") >= 0;
  public static final boolean isIbmJvm = JAVA_VENDOR.indexOf("IBM") >= 0;
  public static final boolean isAzulJvm = JAVA_VENDOR.indexOf("Azul") >= 0;
  public static final boolean isJetBrainsJvm = JAVA_VENDOR.indexOf("JetBrains") >= 0;

  @SuppressWarnings("SpellCheckingInspection")
  private static boolean isCrostini() {
    return new File("/dev/.cros_milestone").exists();
  }

  public static boolean isOsVersionAtLeast( String version) {
    return compareVersionNumbers(OS_VERSION, version) >= 0;
  }
private static int compareVersionNumbers( String v1,  String v2) {
  // todo duplicates com.intellij.util.text.VersionComparatorUtil.compare
  // todo please refactor next time you make changes here
  if (v1 == null && v2 == null) {
    return 0;
  }
  if (v1 == null) {
    return -1;
  }
  if (v2 == null) {
    return 1;
  }

  String[] part1 = v1.split("[._\\-]");
  String[] part2 = v2.split("[._\\-]");

  int idx = 0;
  for (; idx < part1.length && idx < part2.length; idx++) {
    String p1 = part1[idx];
    String p2 = part2[idx];

    int cmp;
    if (p1.matches("\\d+") && p2.matches("\\d+")) {
      cmp = Integer.valueOf(p1).compareTo(Integer.valueOf(p2));
    }
    else {
      cmp = part1[idx].compareTo(part2[idx]);
    }
    if (cmp != 0) return cmp;
  }

  if (part1.length != part2.length) {
    boolean left = part1.length > idx;
    String[] parts = left ? part1 : part2;

    for (; idx < parts.length; idx++) {
      String p = parts[idx];
      int cmp;
      if (p.matches("\\d+")) {
        cmp = Integer.valueOf(p).compareTo(0);
      }
      else {
        cmp = 1;
      }
      if (cmp != 0) return left ? cmp : -cmp;
    }
  }
  return 0;
}
  public static final boolean isWin8OrNewer = isWindows && isOsVersionAtLeast("6.2");
  public static final boolean isWin10OrNewer = isWindows && isOsVersionAtLeast("10.0");
  public static final boolean isWin11OrNewer = isWindows && isOsVersionAtLeast("11.0");

  public static final boolean isXWindow = SystemInfoRt.isXWindow;
  public static final boolean isWayland, isGNOME, isKDE, isXfce, isI3;
  static {
    // http://askubuntu.com/questions/72549/how-to-determine-which-window-manager-is-running/227669#227669
    // https://userbase.kde.org/KDE_System_Administration/Environment_Variables#KDE_FULL_SESSION
    if (isXWindow) {
      isWayland = System.getenv("WAYLAND_DISPLAY") != null;
      @SuppressWarnings("SpellCheckingInspection") String desktop = System.getenv("XDG_CURRENT_DESKTOP"), gdmSession = System.getenv("GDMSESSION");
      isGNOME = desktop != null && desktop.contains("GNOME") || gdmSession != null && gdmSession.contains("gnome");
      isKDE = !isGNOME && (desktop != null && desktop.contains("KDE") || System.getenv("KDE_FULL_SESSION") != null);
      isXfce = !isGNOME && !isKDE && (desktop != null && desktop.contains("XFCE"));
      isI3 = !isGNOME && !isKDE && !isXfce && (desktop != null && desktop.contains("i3"));
    }
    else {
      isWayland = isGNOME = isKDE = isXfce = isI3 = false;
    }
  }

  public static final boolean isMacSystemMenu = isMac && (SystemInfoRt.isJBSystemMenu || Boolean.getBoolean("apple.laf.useScreenMenuBar"));

  public static final boolean isFileSystemCaseSensitive = SystemInfoRt.isFileSystemCaseSensitive;



  public static final boolean isMacOSCatalina = isMac && isOsVersionAtLeast("10.15");
  public static final boolean isMacOSBigSur = isMac && isOsVersionAtLeast("10.16");
  public static final boolean isMacOSMonterey = isMac && isOsVersionAtLeast("12.0");
  public static final boolean isMacOSVentura = isMac && isOsVersionAtLeast("13.0");
  public static final boolean isMacOSSonoma = isMac && isOsVersionAtLeast("14.0");



  public static String getOsName() {
    return isMac ? "macOS" : OS_NAME;
  }

  public static String getOsNameAndVersion() {
    return getOsName() + ' ' + OS_VERSION;
  }

  private static int normalize(int number) {
    return Math.min(number, 9);
  }

  private static int toInt(String string) {
    try {
      return Integer.parseInt(string);
    }
    catch (NumberFormatException e) {
      return 0;
    }
  }

}
