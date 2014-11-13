/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests;

import lineage2.gameserver.model.instances.NpcInstance;
import lineage2.gameserver.model.quest.Quest;
import lineage2.gameserver.model.quest.QuestState;
import lineage2.gameserver.scripts.ScriptFile;

/**
 * @author KilRoy
 * @name 469 - Suspicious Gardener
 * @category Daily quest. Party
 */
public class Q00469_SuspiciousGardener extends Quest implements ScriptFile
{
	private static final int HORPINA = 33031;
	private static final int APHERUS_WATCHMAN = 22964;
	private static final int CERTIFICATE_OF_LIFE = 30385;
	private static final String APHERUS_WATCHMAN_KILL = "watchman";
	
	@Override
	public void onLoad()
	{
	}
	
	@Override
	public void onReload()
	{
	}
	
	@Override
	public void onShutdown()
	{
	}
	
	public Q00469_SuspiciousGardener()
	{
		super(2);
		addTalkId(HORPINA);
		addKillNpcWithLog(1, APHERUS_WATCHMAN_KILL, 30, APHERUS_WATCHMAN);
		addLevelCheck(90, 99);
	}
	
	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		
		if (event.equalsIgnoreCase("quest_accpted"))
		{
			st.setState(STARTED);
			st.setCond(1);
			st.playSound(SOUND_ACCEPT);
			htmltext = "33031-03.htm";
		}
		
		if (event.equalsIgnoreCase("quest_done"))
		{
			st.giveItems(CERTIFICATE_OF_LIFE, 2);
			st.exitCurrentQuest(this);
			st.playSound(SOUND_FINISH);
			htmltext = "33031-06.htm";
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		int cond = st.getCond();
		int npcId = npc.getId();
		String htmltext = "noquest";
		
		if (npcId == HORPINA)
		{
			if (st.isCreated() && !st.isNowAvailableByTime())
			{
				htmltext = "33031-04.htm";
			}
			else if ((cond == 0) && isAvailableFor(st.getPlayer()))
			{
				htmltext = "33031-01.htm";
			}
			else if (cond == 1)
			{
				htmltext = "33031-03.htm";
			}
			else if (cond == 2)
			{
				htmltext = "33031-05.htm";
			}
			else
			{
				htmltext = "33032-07.htm";
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(NpcInstance npc, QuestState st)
	{
		boolean doneKill = updateKill(npc, st);
		
		if (doneKill)
		{
			st.unset(APHERUS_WATCHMAN_KILL);
			st.playSound(SOUND_MIDDLE);
			st.setCond(2);
		}
		
		return null;
	}
}