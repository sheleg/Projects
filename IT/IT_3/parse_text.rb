def get_dict(order)
  file = open("input_text.txt")

  word_array = file.read.gsub("\n", ' ').gsub(/\s+/, ' ').gsub(/[^а-я ]/i, '').downcase.split(" ")
  dictionary = {}

  for i in 0..word_array.count - order - 1 do
    chain = word_array.slice(i, order + 1)
    key = chain.pop
    if dictionary.key?(chain)
      if dictionary[chain].key?(key)
        dictionary[chain][key] += 1
      else
        internal_hash = {}
        internal_hash[key] = 1
        dictionary[chain].merge!(internal_hash)
      end
    else
      internal_hash = {}
      internal_hash[key] = 1
      dictionary[chain] = internal_hash
    end
  end
  dictionary
end
