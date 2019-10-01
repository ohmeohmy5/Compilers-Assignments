
class TrieNode
{
    TrieNode[] arr;
    boolean endOfWord;

    public TrieNode()
    {
    	this.arr = new TrieNode[26];
	}
	 
}
	 
public class Trie
{
    private TrieNode root;
 
    public Trie()
    {
        root = new TrieNode();
    }
 
    public void add(String word)
    {
        TrieNode t = root;
        
        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            int index = c-'a';
            
            if(t.arr[index] == null)
            {
                TrieNode temp = new TrieNode();
                t.arr[index] = temp;
                t = temp;
            }
            
            else
            {
                t = t.arr[index];
            }
        }
        
        t.endOfWord = true;
    }
    
    public TrieNode search(String s)
    {
        TrieNode t = root;
        
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            int index = c-'a';
            
            if(t.arr[index] != null)
            {
                t = t.arr[index];
            }
            
            else
            {
                return null;
            }
        }
 
        if(t == root)
        {
            return null;
        }
        
        return t;
    }
 
    public boolean isMember(String word)
    {
        TrieNode p = search(word);
        
        if(p == null)
        {
            return false;
        }
        
        else
        {
            if(p.endOfWord)
                return true;
        }
 
        return false;
    }
 
    public boolean startsWith(String prefix)
    {
        TrieNode t = search(prefix);
        
        if(t == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
 
	}
}
