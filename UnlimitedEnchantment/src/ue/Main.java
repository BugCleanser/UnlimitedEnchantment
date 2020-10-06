
package ue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

import top.dsbbs2.common.command.CommandRegistry;

public class Main extends JavaPlugin
{
  @Override
  public void onEnable()
  {
    CommandRegistry.regComWithCompleter(this.getName(), CommandRegistry.setComDesc(CommandRegistry.setComUsa(CommandRegistry.setComPer(CommandRegistry.newPluginCommand("ue",this,this),"ue.use"),"/ue (<enchantment> [level])..."),"��������Ʒ��Ӹ�ħ"));
    CommandRegistry.regComWithCompleter(this.getName(), CommandRegistry.setComDesc(CommandRegistry.setComUsa(CommandRegistry.setComPer(CommandRegistry.newPluginCommand("re",this,this),"ue.use"),"/re [enchantment]..."),"��������Ʒ�Ƴ���ħ"));
  }
  public static boolean isInt(String str)
  {
    try {
      Integer.valueOf(str);
      return true;
    }catch (Throwable e) {
      return false;
    }
  }
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if ("ue".equalsIgnoreCase(command.getName())) {
      if (args.length<1) {
        return false;
      }
      if (!(sender instanceof Player)) {
        sender.sendMessage("�㲻�����!");
        return true;
      }
      Player player=(Player)sender;
      if (player.getInventory().getItemInMainHand()==null||player.getInventory().getItemInMainHand().getType()==Material.AIR) {
        sender.sendMessage("����������Ʒ!");
        return true;
      }
      Enchantment ench=null;
      boolean skip=false;
      for(String i : args)
      {
        if (isInt(i)) {
          if(skip)
          {
            skip=false;
            continue;
          }
          if(ench==null)
            return false;
          try {
            player.getInventory().getItemInMainHand().addUnsafeEnchantment(ench, Integer.valueOf(i));
          }catch (Throwable e) {
            sender.sendMessage("��Ӹ�ħ"+(ench.getKey().getNamespace()+":"+ench.getKey().getKey())+"ʱ�����쳣: "+e.toString());
          }
          ench=null;
          continue;
        }
        skip=false;
        if (ench!=null) {
          try {
            player.getInventory().getItemInMainHand().addUnsafeEnchantment(ench, 1);
          }catch (Throwable e) {
            sender.sendMessage("��Ӹ�ħ"+(ench.getKey().getNamespace()+":"+ench.getKey().getKey())+"ʱ�����쳣: "+e.toString());
          }
          ench=null;
        }
        List<Enchantment> tmp=Lists.newArrayList(Enchantment.values()).parallelStream().filter(i2->i.contains(":")?(i2.getKey().getNamespace()+":"+i2.getKey().getKey()).toLowerCase(Locale.ENGLISH).startsWith(i.toLowerCase(Locale.ENGLISH)):i2.getKey().getKey().toLowerCase(Locale.ENGLISH).startsWith(i.toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
        if (tmp.size()==1) {
          ench=tmp.get(0);
        }else if (tmp.size()>1) {
          sender.sendMessage("��������"+i+"�ж�����������ĸ�ħ: "+Arrays.toString(tmp.parallelStream().map(i2->i2.getKey().getNamespace()+":"+i2.getKey().getKey()).toArray(String[]::new)));
          skip=true;
        }else {
          sender.sendMessage("��������"+i+"û��ƥ��ĸ�ħ");
          skip=true;
        }
      }
      return true;
    }else if ("re".equalsIgnoreCase(command.getName())) {
      if (!(sender instanceof Player)) {
        sender.sendMessage("�㲻�����!");
        return true;
      }
      Player player=(Player)sender;
      if (player.getInventory().getItemInMainHand()==null||player.getInventory().getItemInMainHand().getType()==Material.AIR) {
        sender.sendMessage("����������Ʒ!");
        return true;
      }
      if (args.length<1) {
        try {
          player.getInventory().getItemInMainHand().getEnchantments().entrySet().forEach(i2->player.getInventory().getItemInMainHand().removeEnchantment(i2.getKey()));
        }catch (Throwable e) {
          sender.sendMessage("�Ƴ�ȫ����ħʱ�����쳣: "+e.toString());
        }
      }else {
        for(String i : args)
        {
          List<Enchantment> tmp=player.getInventory().getItemInMainHand().getEnchantments().keySet().parallelStream().filter(i2->i.contains(":")?(i2.getKey().getNamespace()+":"+i2.getKey().getKey()).toLowerCase(Locale.ENGLISH).startsWith(i.toLowerCase(Locale.ENGLISH)):i2.getKey().getKey().toLowerCase(Locale.ENGLISH).startsWith(i.toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
          if (tmp.size()==1) {
            try {
              player.getInventory().getItemInMainHand().removeEnchantment(tmp.get(0));
            }catch (Throwable e) {
              sender.sendMessage("�Ƴ���ħ"+(tmp.get(0).getKey().getNamespace()+":"+tmp.get(0).getKey().getKey())+"ʱ�����쳣: "+e.toString());
            }
          }else if (tmp.size()>1) {
            sender.sendMessage("��������"+i+"�ж�����������ĸ�ħ: "+Arrays.toString(tmp.parallelStream().map(i2->i2.getKey().getNamespace()+":"+i2.getKey().getKey()).toArray(String[]::new)));
          }else {
            sender.sendMessage("��������"+i+"û��ƥ��ĸ�ħ");
          }
        }
      }
    }
    return super.onCommand(sender, command, label, args);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
  {
    if ("ue".equalsIgnoreCase(command.getName())) {
      if(args.length<2)
      {
        String i=args.length<1?"":args[args.length-1];
        return Lists.newArrayList(Enchantment.values()).parallelStream().filter(i2->i.contains(":")?(i2.getKey().getNamespace()+":"+i2.getKey().getKey()).toLowerCase(Locale.ENGLISH).startsWith(i.toLowerCase(Locale.ENGLISH)):i2.getKey().getKey().toLowerCase(Locale.ENGLISH).startsWith(i.toLowerCase(Locale.ENGLISH))).map(i2->i2.getKey().getNamespace()+":"+i2.getKey().getKey()).collect(Collectors.toList());
      }else {
        String i=args[args.length-1];
        String last=args[args.length-2];
        List<String> r=new ArrayList<>();
        if(!isInt(last))
          r.add("[int]");
        r.addAll(Lists.newArrayList(Enchantment.values()).parallelStream().filter(i2->i.contains(":")?(i2.getKey().getNamespace()+":"+i2.getKey().getKey()).toLowerCase(Locale.ENGLISH).startsWith(i.toLowerCase(Locale.ENGLISH)):i2.getKey().getKey().toLowerCase(Locale.ENGLISH).startsWith(i.toLowerCase(Locale.ENGLISH))).map(i2->i2.getKey().getNamespace()+":"+i2.getKey().getKey()).collect(Collectors.toList()));
        return r;
      }
    }else if ("re".equalsIgnoreCase(command.getName())) {
      if (!(sender instanceof Player)) {
        return Lists.newArrayList();
      }
      Player player=(Player)sender;
      if (player.getInventory().getItemInMainHand()==null||player.getInventory().getItemInMainHand().getType()==Material.AIR) {
        return Lists.newArrayList();
      }
      String i=args.length<1?"":args[args.length-1];
      return player.getInventory().getItemInMainHand().getEnchantments().keySet().parallelStream().filter(i2->i.contains(":")?(i2.getKey().getNamespace()+":"+i2.getKey().getKey()).toLowerCase(Locale.ENGLISH).startsWith(i.toLowerCase(Locale.ENGLISH)):i2.getKey().getKey().toLowerCase(Locale.ENGLISH).startsWith(i.toLowerCase(Locale.ENGLISH))).map(i2->i2.getKey().getNamespace()+":"+i2.getKey().getKey()).collect(Collectors.toList());
    }
    return super.onTabComplete(sender, command, alias, args);
  }
  
}
