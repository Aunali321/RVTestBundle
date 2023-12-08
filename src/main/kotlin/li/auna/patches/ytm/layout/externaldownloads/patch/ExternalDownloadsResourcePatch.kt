package li.auna.patches.ytm.layout.externaldownloads.patch

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.util.ResourceGroup
import app.revanced.util.copyResources

@Patch(
    name = "External Downloads Patch",
    description = "Adds support to download and save YouTube Music songs to your device.",
    compatiblePackages = [
        CompatiblePackage(
            "com.google.android.apps.youtube.music",
            [
                "6.08.50",
                "6.15.51"
            ]
        )
    ]
)
object ExternalDownloadsResourcePatch : ResourcePatch() {
    override fun execute(context: ResourceContext) {

        context.copyResources("downloads", ResourceGroup("drawable", "revanced_yt_download_button.xml"))

        context.xmlEditor["res/layout/music_menu_like_buttons.xml"].also { editor ->
//            Add the download button to the layout

            val children = editor.file.getElementsByTagName("LinearLayout").item(0).childNodes
            val child = editor.file.createElement("com.google.android.libraries.youtube.common.ui.TouchImageView")
            child.setAttribute("android:id", "@+id/external_download_button")
            child.setAttribute("android:padding", "@dimen/remix_overlay_player_control_button_padding")
            child.setAttribute("android:layout_width", "wrap_content")
            child.setAttribute("android:layout_height", "wrap_content")
            child.setAttribute("android:src", "@drawable/revanced_yt_download_button")
            child.setAttribute("android:tint", "@color/ytm_icon_color_active")
            child.setAttribute("android:contentDescription", "@string/accessibility_dislike_video")
            child.setAttribute("style", "@style/MusicPlayerButton")
            editor.file.getElementsByTagName("LinearLayout").item(0).insertBefore(child, children.item(0))
        }.close()
    }
}