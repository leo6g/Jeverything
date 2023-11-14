// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package only.leo.wfm.core.runnable.idea;

public enum CpuArch {
  X86(32), X86_64(64), ARM32(32), ARM64(64), OTHER(0), UNKNOWN(0);

  /**
   * Machine word size, in bits.
   */
  public final int width;

  CpuArch(int width) {
    if (width == 0) {
      try { width = Integer.parseInt(System.getProperty("sun.arch.data.model", "32")); }
      catch (NumberFormatException ignored) { }
    }
    this.width = width;
  }

  /**
   * <p>A CPU architecture this Java VM is executed on.
   * Here, {@link CpuArch#OTHER} is an architecture not yet supported by JetBrains Runtime,
   * and {@link CpuArch#UNKNOWN} means the code was unable to detect an architecture.</p>
   *
   * <p><b>Note</b>: may not correspond to the actual hardware if a JVM is "virtualized" (e.g. WoW64 or Rosetta 2).</p>
   */
  public static final CpuArch CURRENT = fromString(System.getProperty("os.arch"));

  public static CpuArch fromString(String arch) {
    if ("x86_64".equals(arch) || "amd64".equals(arch)) return X86_64;
    if ("i386".equals(arch) || "x86".equals(arch)) return X86;
    if ("aarch64".equals(arch) || "arm64".equals(arch)) return ARM64;
    return arch == null || arch.trim().isEmpty() ? UNKNOWN : OTHER;
  }

  public static boolean isIntel32() { return CURRENT == X86; }
  public static boolean isIntel64() { return CURRENT == X86_64; }
  public static boolean isArm32() { return CURRENT == ARM32; }
  public static boolean isArm64() { return CURRENT == ARM64; }

  public static boolean is32Bit() { return CURRENT.width == 32; }


  private static  Boolean ourEmulated;

  //</editor-fold>
}
