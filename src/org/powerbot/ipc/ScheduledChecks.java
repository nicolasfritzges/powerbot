package org.powerbot.ipc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import org.powerbot.core.bot.Bot;
import org.powerbot.core.bot.handlers.ScriptHandler;
import org.powerbot.service.NetworkAccount;
import org.powerbot.service.scripts.ScriptDefinition;
import org.powerbot.util.Configuration;

/**
 * @author Paris
 */
public final class ScheduledChecks implements ActionListener {
	private final static Logger log = Logger.getLogger(ScheduledChecks.class.getName());
	public static volatile long SESSION_TIME = 0;
	public static final int LOCALSCRIPT_TIMEOUT = 60 * 3;

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (!Controller.getInstance().isBound() || Controller.getInstance().getRunningInstances() < 1) {
			System.exit(1);
		}

		if (Bot.isInstantiated() && Bot.getInstance().getScriptHandler() != null) {
			final ScriptHandler script = Bot.getInstance().getScriptHandler();
			final ScriptDefinition definition;
			if ((definition = script.getDefinition()) != null && definition.local &&
					script.started < System.currentTimeMillis() - 1000 * LOCALSCRIPT_TIMEOUT && script.isActive() &&
					!NetworkAccount.getInstance().hasPermission(NetworkAccount.Permissions.DEVELOPER)) {
				log.info("Local script stopped after timeout for unauthorised developer");
				script.stop();
			}
		}

		if (Configuration.VERSION < Configuration.VERSION_LATEST || Configuration.VERSION_LATEST == -1) {
			System.exit(1);
		}
	}
}
