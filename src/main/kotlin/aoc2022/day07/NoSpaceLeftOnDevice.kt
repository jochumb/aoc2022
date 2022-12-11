package aoc2022.day07

object NoSpaceLeftOnDevice {

    fun sumDirsWithSizesOfAtMost100000(input: List<String>): Int {
        val root = parseToDirectoryTree(input)
        return root.flatDirs().filter { dir -> dir.size() <= 100_000 }.sumOf { it.size() }
    }

    fun sizeOfSmallestDirToFreeEnoughSpace(input: List<String>): Int {
        val root = parseToDirectoryTree(input)
        val requiredSpace = 30_000_000 - (70_000_000 - root.size())
        return root.flatDirs().map { it.size() }.sorted().first { it >= requiredSpace }
    }

    private fun parseToDirectoryTree(input: List<String>): Dir =
        exec(input.map { it.toCommand() }, Dir("/"))

    private tailrec fun exec(commands: List<Command>, dir: Dir): Dir {
        if (commands.isEmpty()) return returnToRoot(dir)

        return when (val command = commands.first()) {
            is ChangeDirParent -> exec(commands.next(), dir.parent!!.addDir(dir))
            is ChangeDir       -> exec(commands.next(), Dir(command.name, dir))
            is ListFile        -> exec(commands.next(), dir.addFile(command.asFile))
            else               -> exec(commands.next(), dir)
        }
    }

    private tailrec fun returnToRoot(dir: Dir): Dir =
        when (val parent = dir.parent) {
            null -> dir
            else -> returnToRoot(parent.addDir(dir))
        }

    private data class File(val name: String, val size: Int)
    private data class Dir(
        val name: String,
        val parent: Dir? = null,
        val dirs: List<Dir> = emptyList(),
        val files: List<File> = emptyList()
    ) {
        fun size(): Int = dirs.sumOf { it.size() } + files.sumOf { it.size }
        fun flatDirs(): List<Dir> = dirs + dirs.flatMap { it.flatDirs() }

        fun addDir(dir: Dir): Dir = this.copy(dirs = this.dirs + dir)
        fun addFile(file: File): Dir = this.copy(files = this.files + file)
    }

    private sealed interface Command
    private object ChangeDirParent : Command
    private object ShowDir : Command
    private object ListDir : Command
    private data class ChangeDir(val name: String) : Command
    private data class ListFile(val name: String, val size: Int) : Command {
        val asFile: File = File(name = this.name, size = this.size)
    }

    private fun String.toCommand(): Command =
        when {
            this.startsWith("$ cd ..") -> ChangeDirParent
            this.startsWith("$ cd ")   -> ChangeDir(name = this.substringAfterLast(" "))
            this.startsWith("$ ls")    -> ShowDir
            this.startsWith("dir")     -> ListDir
            else                       -> ListFile(
                name = this.substringAfter(" "),
                size = this.substringBefore(" ").toInt()
            )
        }

    private fun List<Command>.next(): List<Command> = this.drop(1)
}

