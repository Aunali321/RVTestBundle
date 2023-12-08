package li.auna.patches.ytm.misc.integrations.patch

import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patches.shared.integrations.AbstractIntegrationsPatch
import li.auna.patches.ytm.misc.integrations.fingerprints.InitFingerprint

@Patch(requiresIntegrations = true)
object IntegrationsPatch : AbstractIntegrationsPatch(
    "Lapp/revanced/integrations/utils/ReVancedUtils;",
    setOf(InitFingerprint)
)