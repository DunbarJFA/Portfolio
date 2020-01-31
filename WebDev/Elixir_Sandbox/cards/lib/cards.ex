defmodule Cards do
  @moduledoc """
   Provides methods for creating and handling a deck of cards

   ## Examples
      iex> deck = Cards.create_deck
      iex> {hand, deck} = Cards.deal(deck, 1)
      iex> hand
      ["Ace of Spades"]
  """
  @doc """
   Returns a string representing a deck of playing cards
  """
  def create_deck do
    values = ["Ace", "Two", "Three", "Four", "Five"]
    suites = ["Spades", "Hearts", "Clubs", "Diamonds"]
    # List Comprehension
    for suite <- suites, value <- values do
      "#{value} of #{suite}"
    end
  end
  @doc """
    Returns a given deck with the order of its member cards randomized
  """
  def shuffle(deck) do
    Enum.shuffle(deck)
  end

  @doc """
    Returns a boolean regarding whether a specific card exists in the given deck

    ## Examples

        iex> deck = Cards.create_deck
        iex> Cards.contains?(deck, "Ace of Spades")
        true
  """
  def contains?(deck, card) do
    Enum.member?(deck, card)
  end

  @doc """
    Returns a list of `hand_size` number of cards from the given deck.
  """
  def deal(deck, hand_size) do
    # returns a "tuple" called "hand"
    Enum.split(deck, hand_size)
    # tuple is a data structure with symbolic parts
    # these parts can be utilized with "Pattern Matching"
    # essentially, match a tuple of variable names with another tuple of values with the same structure
    # elixir matches the values from the latter with the names from the former to create new variables
  end
  @doc """
    Saves a deck to the local machine as a file
  """
  def save_deck(deck, filename) do
      binary = :erlang.term_to_binary(deck)
      File.write(filename, binary)
  end

  @doc """
    Retrieves a deck file from the local machine
  """
  def load_deck(filename) do
    # Without Pattern Matching
    #{status, binary} = File.read(filename)

    # case status do
    #    :ok-> :erlang.binary_to_term(binary)
    #    :error-> "That deck does not exist!"
    # end
    # With Pattern Matching
      case File.read(filename) do
         {:ok, binary}-> :erlang.binary_to_term(binary)
         {:error, _reason}-> "That deck does not exist!"
      end
    end

    @doc """
      Combines the `create_deck`,`shuffle`, and `deal` functions to return a hand of `hand_size` cards
    """
  def create_hand(hand_size) do
    # Pipe Operator Example
    # Passes results of preceding function to following function starting with the first argument
      Cards.create_deck|>Cards.shuffle|>Cards.deal(hand_size)
  end
end
