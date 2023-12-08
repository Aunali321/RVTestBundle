package li.auna.patches.youtube.layout.hide.subtitlestoast.patch

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.removeInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patches.shared.settings.preference.impl.StringResource
import app.revanced.patches.shared.settings.preference.impl.SwitchPreference
import li.auna.patches.youtube.layout.hide.subtitlestoast.fingerprints.HideSubtitlesToastFingerprint
import li.auna.patches.youtube.layout.hide.subtitlestoast.fingerprints.HideSubtitlesToastFingerprint2
import app.revanced.patches.youtube.misc.integrations.IntegrationsPatch
import app.revanced.patches.youtube.misc.settings.SettingsPatch
import app.revanced.util.exception

@Patch(
    name = "Hide subtitles toast",
    description = "Hides the subtitles toast when toggling subtitles",
    dependencies = [
        IntegrationsPatch::class,
        SettingsPatch::class
    ],
    compatiblePackages = [
        CompatiblePackage(
            "com.google.android.youtube",
            [
                "18.16.37",
                "18.19.35",
                "18.20.39",
                "18.23.35",
                "18.30.37",
                "18.32.32",
                "18.45.41",
                "18.45.43"
            ]
        )
    ],
    requiresIntegrations = true
)
object HideSubtitlesToastPatch : BytecodePatch(
    setOf(HideSubtitlesToastFingerprint, HideSubtitlesToastFingerprint2)
) {
    override fun execute(context: BytecodeContext) {

//        Uncomment when you can use 2 patch bundles with settings in revanced cli and revanced manager.
        SettingsPatch.PreferenceScreen.INTERACTIONS.addPreferences(
            SwitchPreference(
                "revanced_hide_subtitles_toast",
                StringResource("revanced_hide_subtitles_toast_title", "Hide subtitles toast"),
                StringResource("revanced_hide_subtitles_toast_summary_on", "Subtitles toast is enabled"),
                StringResource("revanced_hide_subtitles_toast_summary_off", "Subtitles toast is disabled"),
            )
        )

//        HideSubtitlesToastFingerprint.result?.mutableMethod?.addInstructions(
//                0,
//                """
//                    invoke-static {}, Lapp/revanced/integrations/patches/HideSubtitlesToastPatch;->hideSubtitlesToast()Z
//                    move-result v0
//                    if-eqz v0, :cond_0
//                    return-void
//                    :cond_0
//                    nop
//                """
//        )
//                ?: throw PatchException("Failed to find method")

//
//
        HideSubtitlesToastFingerprint.result?.mutableMethod?.addInstructions(0, "return-void")
            ?: throw HideSubtitlesToastFingerprint.exception

        HideSubtitlesToastFingerprint2.result?.mutableMethod?.apply {
//            remove the instruction that sets the toast text
            val lastIndex = implementation!!.instructions.lastIndex - 2
            removeInstruction(lastIndex)
        }
    }
}